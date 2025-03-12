package com.compass.ms_event_manager.controller;

import com.compass.ms_event_manager.model.Event;
import com.compass.ms_event_manager.service.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class EventControllerTest {

    @InjectMocks
    private EventController eventController;

    @Mock
    private EventService eventService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createEvent_ShouldReturnCreatedEvent() {
        Event event = new Event();
        event.setEventName("Ingresso pra cachoeira");
        event.setDateTime(LocalDateTime.parse("2025-03-22T21:30:00"));
        event.setCep("72980-970");

        when(eventService.createEvent(event)).thenReturn(event);

        ResponseEntity<Event> response = eventController.createEvent(event);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(event, response.getBody());
        verify(eventService, times(1)).createEvent(event);
    }

    @Test
    void getEventById_ShouldReturnEvent() {
        String eventId = "1";
        Event event = new Event();
        event.setId(eventId);

        when(eventService.getEventById(eventId)).thenReturn(event);

        ResponseEntity<Event> response = eventController.getEventById(eventId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(event, response.getBody());
        verify(eventService, times(1)).getEventById(eventId);
    }

    @Test
    void getAllEvents_ShouldReturnAllEvents() {
        List<Event> events = Arrays.asList(new Event(), new Event());

        when(eventService.getAllEvents()).thenReturn(events);

        ResponseEntity<List<Event>> response = eventController.getAllEvents();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(events, response.getBody());
        verify(eventService, times(1)).getAllEvents();
    }

    @Test
    void getAllEventsSorted_ShouldReturnSortedEvents() {
        List<Event> sortedEvents = Arrays.asList(new Event(), new Event());

        when(eventService.getAllEventsSorted()).thenReturn(sortedEvents);

        ResponseEntity<List<Event>> response = eventController.getAllEventsSorted();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(sortedEvents, response.getBody());
        verify(eventService, times(1)).getAllEventsSorted();
    }

    @Test
    void updateEvent_ShouldReturnUpdatedEvent() {
        String eventId = "1";
        Event updatedEvent = new Event();
        updatedEvent.setEventName("Updated Event");

        when(eventService.updateEvent(eventId, updatedEvent)).thenReturn(updatedEvent);

        ResponseEntity<Event> response = eventController.updateEvent(eventId, updatedEvent);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedEvent, response.getBody());
        verify(eventService, times(1)).updateEvent(eventId, updatedEvent);
    }

    @Test
    void deleteEvent_ShouldReturnNoContent() {
        String eventId = "1";

        doNothing().when(eventService).deleteEvent(eventId);

        ResponseEntity<Void> response = eventController.deleteEvent(eventId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(eventService, times(1)).deleteEvent(eventId);
    }
}