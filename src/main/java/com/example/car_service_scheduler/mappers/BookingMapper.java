package com.example.car_service_scheduler.mappers;


import com.example.car_service_scheduler.dto.request.response.BookingResponse;
import com.example.car_service_scheduler.dto.request.response.RescheduleResponse;
import com.example.car_service_scheduler.entity.Appointment;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.time.LocalTime;

@Mapper
public interface BookingMapper {
    BookingMapper INSTANCE = Mappers.getMapper(BookingMapper.class);

    @Mapping(target = "appointmentId", source = "id")
    @Mapping(target = "customerName", source = "customer.name")
    @Mapping(target = "operatorName", source = "operator.name")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "appointmentDate", source = "appointmentDate")
    @Mapping(target = "startTime", source = "startTime")
    BookingResponse bookingToBookingResponse(Appointment booking);

    @Mapping(target = "appointmentId", source = "id")
    @Mapping(target = "newDate", source = "appointmentDate")
    @Mapping(target = "newStartTime", source = "startTime")
    @Mapping(target = "newEndTime", source = "endTime")
    RescheduleResponse bookingToRescheduleResponse(Appointment booking);

}