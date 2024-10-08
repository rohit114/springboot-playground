package com.example.car_service_scheduler.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponse {
    private String message;
    private int statusCode;
    private long timestamp;
    private String traceId;

    public ErrorResponse(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
        this.timestamp = System.currentTimeMillis();
        this.traceId = traceId;
    }
}
