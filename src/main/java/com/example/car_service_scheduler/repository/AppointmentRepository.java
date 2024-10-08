package com.example.car_service_scheduler.repository;


import com.example.car_service_scheduler.entity.Appointment;
import com.example.car_service_scheduler.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    // Find appointments by operator, date, and start time with status 'BOOKED'
    Optional<Appointment> findByOperatorIdAndAppointmentDateAndStartTimeAndStatus(
            Long operatorId, LocalDate appointmentDate, LocalTime startTime, AppointmentStatus status);

    // Find all booked appointments for an operator
    List<Appointment> findByOperatorIdAndStatus(Long operatorId, AppointmentStatus status);

    // Find all booked appointments for an operator on a specific date
    List<Appointment> findByOperatorIdAndAppointmentDateAndStatus(Long operatorId, LocalDate appointmentDate, AppointmentStatus status);

    // Find all appointments for an operator and date
    List<Appointment> findByOperatorIdAndAppointmentDate(Long operatorId, LocalDate appointmentDate);

    // Find all booked operator IDs for a specific date and time
    List<Long> findDistinctOperatorIdByAppointmentDateAndStartTimeAndStatus(LocalDate appointmentDate, LocalTime startTime, AppointmentStatus status);
}
