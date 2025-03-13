package com.compass.ms_event_manager.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EventNotFoundException.class)
    public ResponseEntity<String> handleEventNotFoundException(EventNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(EventRetrievalException.class)
    public ResponseEntity<String> handleEventRetrievalException(EventRetrievalException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao recuperar eventos: " + ex.getMessage());
    }

    @ExceptionHandler(InvalidEventDataException.class)
    public ResponseEntity<String> handleInvalidEventDataException(InvalidEventDataException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(EventCreationException.class)
    public ResponseEntity<String> handleEventCreationException(EventCreationException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao criar evento: " + ex.getMessage());
    }

    @ExceptionHandler(EventDeletionException.class)
    public ResponseEntity<String> handleEventDeletionException(EventDeletionException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(TicketServiceUnavailableException.class)
    public ResponseEntity<String> handleTicketServiceUnavailableException(TicketServiceUnavailableException ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body("Serviço de ingressos indisponível: " + ex.getMessage());
    }

    @ExceptionHandler(EventUpdateException.class)
    public ResponseEntity<String> handleEventUpdateException(EventUpdateException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar evento: " + ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocorreu um erro inesperado: " + ex.getMessage());
    }
}