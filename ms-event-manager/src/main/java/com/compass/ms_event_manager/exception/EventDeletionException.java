package com.compass.ms_event_manager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EventDeletionException extends RuntimeException {
    public EventDeletionException(String message) {
        super(message);
    }
}