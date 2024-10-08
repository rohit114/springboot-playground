package com.example.car_service_scheduler.service;

import com.example.car_service_scheduler.entity.Appointment;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface AppointmentService {
    Appointment bookAppointment(Long customerId, LocalDate appointmentDate, LocalTime startTime, Long operatorId);
    Appointment rescheduleAppointment(Long appointmentId, LocalDate newDate, LocalTime newStartTime, Long newOperatorId);
    Appointment cancelAppointment(Long appointmentId);
    List<Appointment> getBookedAppointments(Long operatorId, LocalDate specificDate);
    List<LocalTime[]> getOpenSlots(Long operatorId, LocalDate dateQuery);
}
