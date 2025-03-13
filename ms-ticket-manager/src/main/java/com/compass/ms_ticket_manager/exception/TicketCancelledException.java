package com.compass.ms_ticket_manager.exception;

public class TicketCancelledException extends RuntimeException {
    public TicketCancelledException(String message) {
        super(message);
    }
}