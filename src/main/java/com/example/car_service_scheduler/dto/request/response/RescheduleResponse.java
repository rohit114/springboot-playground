package com.example.car_service_scheduler.dto.request.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class RescheduleResponse {
    @JsonProperty("id")
    private Long appointmentId;

    @JsonProperty("new_date")
    private LocalDate newDate;

    @JsonProperty("new_start_time")
    private LocalTime newStartTime;

    @JsonProperty("new_end_time")
    private LocalTime newEndTime;
}
