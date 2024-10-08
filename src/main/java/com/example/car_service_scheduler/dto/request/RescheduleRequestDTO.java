package com.example.car_service_scheduler.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public  class RescheduleRequestDTO {
    @JsonProperty("new_date")
    private LocalDate newDate;

    @JsonProperty("new_start_time")
    private LocalTime newStartTime;

    @JsonProperty("new_operator_id")
    private Long newOperatorId; // Optional
}