package com.compass.ms_event_manager.exception;

public class TicketServiceUnavailableException extends RuntimeException {
    public TicketServiceUnavailableException(String message) {
        super(message);
    }
}