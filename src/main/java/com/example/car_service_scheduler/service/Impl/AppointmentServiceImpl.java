package com.example.car_service_scheduler.service.Impl;

import com.example.car_service_scheduler.entity.Appointment;
import com.example.car_service_scheduler.entity.Customer;
import com.example.car_service_scheduler.entity.Operator;
import com.example.car_service_scheduler.enums.AppointmentStatus;

import com.example.car_service_scheduler.repository.AppointmentRepository;
import com.example.car_service_scheduler.repository.CustomerRepository;
import com.example.car_service_scheduler.repository.OperatorRepository;
import com.example.car_service_scheduler.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private OperatorRepository operatorRepository;

    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Book an appointment
     */
    @Transactional
    public Appointment bookAppointment(Long customerId, LocalDate appointmentDate, LocalTime startTime, Long operatorId) {
        // Validate customer existence
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->new IllegalArgumentException("Customer not found with ID: " + customerId ));


        Operator selectedOperator = null;

        if (operatorId != null) {
            // Specific operator booking
            selectedOperator = operatorRepository.findById(operatorId)
                    .orElseThrow(() -> new IllegalArgumentException("Operator not found with ID: " + operatorId));

            // Check if operator is available
            boolean isBooked = appointmentRepository.findByOperatorIdAndAppointmentDateAndStartTimeAndStatus(
                    operatorId, appointmentDate, startTime, AppointmentStatus.BOOKED).isPresent();
            boolean isRescheduled = appointmentRepository.findByOperatorIdAndAppointmentDateAndStartTimeAndStatus(
                    operatorId, appointmentDate, startTime, AppointmentStatus.RESCHEDULED).isPresent();

            if (isBooked || isRescheduled) {
                throw new IllegalArgumentException("Operator is already booked for this time slot.");
            }
        } else {
            // Assign any available operator
            List<Long> bookedOperatorIds = appointmentRepository.findDistinctOperatorIdByAppointmentDateAndStartTimeAndStatus(
                    appointmentDate, startTime, AppointmentStatus.BOOKED);

            selectedOperator = operatorRepository.findAll().stream()
                    .filter(op -> !bookedOperatorIds.contains(op.getId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No operators available for the selected time slot."));
        }

        System.out.println("Hello 1");
        // setting end time 1hr ahead of start
        LocalTime endTime = startTime.plusHours(1);

        // Create and save the appointment
        Appointment appointment = new Appointment();
        appointment.setCustomer(customer);
        appointment.setOperator(selectedOperator);
        appointment.setAppointmentDate(appointmentDate);
        appointment.setStartTime(startTime);
        appointment.setEndTime(endTime);
        appointment.setStatus(AppointmentStatus.BOOKED);
        appointment.setCreatedAt(LocalDateTime.now());
        appointment.setUpdatedAt(LocalDateTime.now());

        System.out.println(appointment);

        return appointmentRepository.save(appointment);
    }

    /**
     * Reschedule an existing appointment
     */
    @Transactional
    public Appointment rescheduleAppointment(Long appointmentId, LocalDate newDate, LocalTime newStartTime, Long newOperatorId) {

        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found with ID: " + appointmentId));

        if (appointment.getStatus() == AppointmentStatus.CANCELLED) {
            throw new IllegalArgumentException("Cannot reschedule a cancelled appointment.");
        }

        Operator selectedOperator = null;

        if (newOperatorId != null) {
            // Specific operator booking
            selectedOperator = operatorRepository.findById(newOperatorId)
                    .orElseThrow(() -> new IllegalArgumentException("Operator not found with ID: " + newOperatorId));

            // Check if operator is available
            boolean isBooked = appointmentRepository.findByOperatorIdAndAppointmentDateAndStartTimeAndStatus(
                    newOperatorId, newDate, newStartTime, AppointmentStatus.BOOKED).isPresent();
            boolean isRescheduled = appointmentRepository.findByOperatorIdAndAppointmentDateAndStartTimeAndStatus(
                    newOperatorId, newDate, newStartTime, AppointmentStatus.RESCHEDULED).isPresent();

            if (isBooked || isRescheduled) {
                throw new IllegalArgumentException("Operator is already booked for the new time slot.");
            }
        } else {
            // Assign any available operator
            List<Long> bookedOperatorIds = appointmentRepository.findDistinctOperatorIdByAppointmentDateAndStartTimeAndStatus(
                    newDate, newStartTime, AppointmentStatus.BOOKED);

            selectedOperator = operatorRepository.findAll().stream()
                    .filter(op -> !bookedOperatorIds.contains(op.getId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("No operators available for the new time slot."));
        }

        // Calculate new end time
        LocalTime newEndTime = newStartTime.plusHours(1);

        // Update appointment details
        appointment.setOperator(selectedOperator);
        appointment.setAppointmentDate(newDate);
        appointment.setStartTime(newStartTime);
        appointment.setEndTime(newEndTime);
        appointment.setStatus(AppointmentStatus.RESCHEDULED);
        appointment.setUpdatedAt(LocalDateTime.now());

        return appointmentRepository.save(appointment);
    }

    /**
     * Cancel an appointment.
     */
    @Transactional
    public Appointment cancelAppointment(Long appointmentId) {
        // Fetch the appointment
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found with ID: " + appointmentId));

        if (appointment.getStatus() == AppointmentStatus.CANCELLED) {
            throw new IllegalArgumentException("Appointment is already cancelled.");
        }

        // Update status
        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointment.setUpdatedAt(LocalDateTime.now());

        return appointmentRepository.save(appointment);
    }

    /**
     * Retrieve all booked appointments for a specific operator.
     */
    @Transactional(readOnly = true)
    public List<Appointment> getBookedAppointments(Long operatorId, LocalDate specificDate) {
        if (specificDate != null) {
            return appointmentRepository.findByOperatorIdAndAppointmentDateAndStatus(
                    operatorId, specificDate, AppointmentStatus.BOOKED);
        } else {
            return appointmentRepository.findByOperatorIdAndStatus(operatorId, AppointmentStatus.BOOKED);
        }
    }

    /**
     * Retrieve open slots for a specific operator on a given date.
     * returns List of open slot ranges represented as LocalTime arrays [start, end]
     */
    @Transactional(readOnly = true)
    public List<LocalTime[]> getOpenSlots(Long operatorId, LocalDate dateQuery) {
        // Define all possible 24 one-hour slots
        List<LocalTime> allSlots = new ArrayList<>();
        for (int hour = 0; hour < 24; hour++) {
            allSlots.add(LocalTime.of(hour, 0));
        }

        // Fetch booked start times
        List<Appointment> bookedAppointments = appointmentRepository.findByOperatorIdAndAppointmentDateAndStatus(
                operatorId, dateQuery, AppointmentStatus.BOOKED);

        Set<LocalTime> bookedSlots = bookedAppointments.stream()
                .map(Appointment::getStartTime)
                .collect(Collectors.toSet());

        // Determine open slots
        List<LocalTime> openSlots = allSlots.stream()
                .filter(slot -> !bookedSlots.contains(slot))
                .collect(Collectors.toList());

        // Merge consecutive open slots
        List<LocalTime[]> mergedSlots = new ArrayList<>();
        if (openSlots.isEmpty()) {
            return mergedSlots;
        }

        openSlots.sort(Comparator.naturalOrder());

        LocalTime rangeStart = openSlots.get(0);
        LocalTime previous = rangeStart;

        for (int i = 1; i < openSlots.size(); i++) {
            LocalTime current = openSlots.get(i);
            if (!current.equals(previous.plusHours(1))) {
                // End current range
                mergedSlots.add(new LocalTime[]{rangeStart, previous.plusHours(1)});
                // Start new range
                rangeStart = current;
            }
            previous = current;
        }

        // Add the last range
        mergedSlots.add(new LocalTime[]{rangeStart, previous.plusHours(1)});

        return mergedSlots;
    }
}
