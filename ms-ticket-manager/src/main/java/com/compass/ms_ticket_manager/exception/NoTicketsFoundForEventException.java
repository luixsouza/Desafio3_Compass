package com.compass.ms_ticket_manager.exception;

public class NoTicketsFoundForEventException extends RuntimeException {
    public NoTicketsFoundForEventException(String message) {
        super(message);
    }
}