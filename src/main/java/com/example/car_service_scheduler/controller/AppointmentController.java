package com.example.car_service_scheduler.controller;

import com.example.car_service_scheduler.dto.request.BookingRequestDTO;
import com.example.car_service_scheduler.dto.request.RescheduleRequestDTO;
import com.example.car_service_scheduler.dto.request.response.BookingResponse;
import com.example.car_service_scheduler.dto.request.response.RescheduleResponse;
import com.example.car_service_scheduler.entity.Appointment;
import com.example.car_service_scheduler.mappers.BookingMapper;
import com.example.car_service_scheduler.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    /**
     * Book an appointment.
     * returns The booked Appointment
     */
    @PostMapping("/book")
    public BookingResponse bookAppointment(@RequestBody BookingRequestDTO bookingRequestDTO) {
        Appointment appointment = appointmentService.bookAppointment(
                bookingRequestDTO.getCustomerId(),
                bookingRequestDTO.getAppointmentDate(),
                bookingRequestDTO.getStartTime(),
                bookingRequestDTO.getOperatorId()
        );
        return BookingMapper.INSTANCE.bookingToBookingResponse(appointment);
    }

    /**
     * Reschedule an appointment.
     * returns The rescheduled Appointment
     */
    @PutMapping("/reschedule/{appointmentId}")
    public RescheduleResponse rescheduleAppointment(
            @PathVariable Long appointmentId,
            @RequestBody RescheduleRequestDTO rescheduleRequestDTO) {
        Appointment appointment = appointmentService.rescheduleAppointment(
                appointmentId,
                rescheduleRequestDTO.getNewDate(),
                rescheduleRequestDTO.getNewStartTime(),
                rescheduleRequestDTO.getNewOperatorId()
        );
        return BookingMapper.INSTANCE.bookingToRescheduleResponse(appointment);
    }

    /**
     * Cancel an appointment.
     */
    @DeleteMapping("/cancel/{appointmentId}")
    public ResponseEntity<?> cancelAppointment(@PathVariable Long appointmentId) {
        Appointment appointment = appointmentService.cancelAppointment(appointmentId);
        return ResponseEntity.ok(appointment.getStatus());
    }

    /**
     * Get booked appointments for an operator, optionally on a specific date.
     * returns List of booked Appointments
     */
    @GetMapping("/booked")
    public List<LocalTime[]> getBookedAppointments(
            @RequestParam("operator_id") Long operatorId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<Appointment> appointments = appointmentService.getBookedAppointments(operatorId, date);
        List<LocalTime[]> bookedAppointmentsList = appointments.stream().map(app -> new LocalTime[]{app.getStartTime(), app.getEndTime()}) // Store name and email in an Object array
                .collect(Collectors.toList());
        return bookedAppointmentsList;
    }

    /**
     * Get open slots for an operator on a specific date
     * returns List of open slot ranges
     */
    @GetMapping("/open-slots")
    public ResponseEntity<?> getOpenSlots(
            @RequestParam("operator_id") Long operatorId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<LocalTime[]> openSlots = appointmentService.getOpenSlots(operatorId, date);
        return ResponseEntity.ok(openSlots);

    }

}
