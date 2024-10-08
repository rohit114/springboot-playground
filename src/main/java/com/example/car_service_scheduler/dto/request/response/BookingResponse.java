package com.example.car_service_scheduler.dto.request.response;


import com.example.car_service_scheduler.enums.AppointmentStatus;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class BookingResponse {

    @JsonProperty("id")
    private Long appointmentId;

    @JsonProperty("operator_name")
    private String operatorName;

    @JsonProperty("customer_name")
    private String customerName;

    @JsonProperty("status")
    private AppointmentStatus status;

    @JsonProperty("appointment_date")
    private LocalDate appointmentDate;

    @JsonProperty("start_time")
    private LocalTime startTime;
}