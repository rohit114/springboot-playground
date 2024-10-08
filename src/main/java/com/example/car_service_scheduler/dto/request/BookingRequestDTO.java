package com.example.car_service_scheduler.dto.request;

import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class BookingRequestDTO {
    @JsonProperty("customer_id")
    private Long customerId;

    @JsonProperty("appointment_date")
    private LocalDate appointmentDate;

    @JsonProperty("start_time")
    private LocalTime startTime;

    @JsonProperty("operator_id")
    private Long operatorId;

}