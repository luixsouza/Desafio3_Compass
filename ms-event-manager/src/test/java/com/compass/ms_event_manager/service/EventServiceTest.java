package com.compass.ms_event_manager.service;

import com.compass.ms_event_manager.client.TicketClient;
import com.compass.ms_event_manager.dto.CheckTicketsResponseDTO;
import com.compass.ms_event_manager.exception.*;
import com.compass.ms_event_manager.mapper.EventMapper;
import com.compass.ms_event_manager.model.Event;
import com.compass.ms_event_manager.repository.EventRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class EventServiceTest {

    @InjectMocks
    private EventService eventService;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private TicketClient ticketClient;

    @Mock
    private EventMapper eventMapper;

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

        when(eventMapper.toCreateEntity(event)).thenReturn(event);
        when(eventRepository.save(event)).thenReturn(event);

        Event createdEvent = eventService.createEvent(event);

        assertEquals(event, createdEvent);
        verify(eventMapper, times(1)).toCreateEntity(event);
        verify(eventRepository, times(1)).save(event);
    }

    @Test
    void createEvent_ShouldThrowEventCreationException() {
        Event event = new Event();
        event.setEventName("Ingresso pra cachoeira");
        event.setDateTime(LocalDateTime.parse("2025-03-22T21:30:00"));
        event.setCep("72980-970");

        when(eventMapper.toCreateEntity(event)).thenThrow(new RuntimeException("Erro no mapper"));

        assertThrows(EventCreationException.class, () -> eventService.createEvent(event));
    }

    @Test
    void createEvent_ShouldThrowInvalidEventDataException() {
        Event event = new Event();

        assertThrows(InvalidEventDataException.class, () -> eventService.createEvent(event));
    }

    @Test
    void getEventById_ShouldReturnEvent() {
        String eventId = "1";
        Event event = new Event();
        event.setId(eventId);

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        Event foundEvent = eventService.getEventById(eventId);

        assertEquals(event, foundEvent);
        verify(eventRepository, times(1)).findById(eventId);
    }

    @Test
    void getEventById_ShouldThrowEventNotFoundException() {
        String eventId = "1";

        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        assertThrows(EventNotFoundException.class, () -> eventService.getEventById(eventId));
    }

    @Test
    void getAllEvents_ShouldReturnAllEvents() {
        List<Event> events = Arrays.asList(new Event(), new Event());

        when(eventRepository.findAll()).thenReturn(events);

        List<Event> foundEvents = eventService.getAllEvents();

        assertEquals(events, foundEvents);
        verify(eventRepository, times(1)).findAll();
    }

    @Test
    void getAllEvents_ShouldThrowEventRetrievalException() {
        when(eventRepository.findAll()).thenThrow(new RuntimeException("Erro no repositório"));

        assertThrows(EventRetrievalException.class, () -> eventService.getAllEvents());
    }

    @Test
    void getAllEventsSorted_ShouldReturnSortedEvents() {
        List<Event> sortedEvents = Arrays.asList(new Event(), new Event());

        when(eventRepository.findAllByOrderByEventNameAsc()).thenReturn(sortedEvents);

        List<Event> foundEvents = eventService.getAllEventsSorted();

        assertEquals(sortedEvents, foundEvents);
        verify(eventRepository, times(1)).findAllByOrderByEventNameAsc();
    }

    @Test
    void getAllEventsSorted_ShouldThrowEventRetrievalException() {
        when(eventRepository.findAllByOrderByEventNameAsc()).thenThrow(new RuntimeException("Erro no repositório"));

        assertThrows(EventRetrievalException.class, () -> eventService.getAllEventsSorted());
    }

    @Test
    void updateEvent_ShouldReturnUpdatedEvent() {
        String eventId = "1";
        Event existingEvent = new Event();
        existingEvent.setId(eventId);
        existingEvent.setEventName("Ingresso pra cachoeira");
        existingEvent.setDateTime(LocalDateTime.parse("2025-03-22T21:30:00"));
        existingEvent.setCep("72980-970");

        Event updatedEvent = new Event();
        updatedEvent.setEventName("Ingresso pra cachoeira - Atualizado");

        Event updatedEventEntity = new Event();
        updatedEventEntity.setId(eventId);
        updatedEventEntity.setEventName("Ingresso pra cachoeira - Atualizado");
        updatedEventEntity.setDateTime(existingEvent.getDateTime());
        updatedEventEntity.setCep(existingEvent.getCep());

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(existingEvent));
        when(eventRepository.save(updatedEventEntity)).thenReturn(updatedEventEntity);
        when(eventMapper.toUpdateEntity(existingEvent, updatedEvent)).thenReturn(updatedEventEntity);

        Event result = eventService.updateEvent(eventId, updatedEvent);

        assertEquals("Ingresso pra cachoeira - Atualizado", result.getEventName());
        assertEquals(existingEvent.getDateTime(), result.getDateTime());
        assertEquals(existingEvent.getCep(), result.getCep());

        verify(eventRepository, times(1)).findById(eventId);
        verify(eventMapper, times(1)).toUpdateEntity(existingEvent, updatedEvent);
        verify(eventRepository, times(1)).save(updatedEventEntity);
    }

    @Test
    void updateEvent_ShouldThrowEventNotFoundException() {
        String eventId = "1";
        Event updatedEvent = new Event();

        when(eventRepository.findById(eventId)).thenReturn(Optional.empty());

        assertThrows(EventNotFoundException.class, () -> eventService.updateEvent(eventId, updatedEvent));
    }

    @Test
    void updateEvent_ShouldThrowInvalidEventDataException() {
        String eventId = "1";
        Event existingEvent = new Event();
        existingEvent.setId(eventId);

        Event updatedEvent = new Event();

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(existingEvent));

        assertThrows(InvalidEventDataException.class, () -> eventService.updateEvent(eventId, updatedEvent));
    }

    @Test
    void updateEvent_ShouldThrowEventUpdateException() {
        String eventId = "1";
        Event existingEvent = new Event();
        existingEvent.setId(eventId);
        existingEvent.setEventName("Ingresso pra cachoeira");
        existingEvent.setDateTime(LocalDateTime.parse("2025-03-22T21:30:00"));
        existingEvent.setCep("72980-970");

        Event updatedEvent = new Event();
        updatedEvent.setEventName("Ingresso pra cachoeira - Atualizado");

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(existingEvent));
        when(eventMapper.toUpdateEntity(existingEvent, updatedEvent)).thenThrow(new RuntimeException("Erro no mapper"));

        assertThrows(EventUpdateException.class, () -> eventService.updateEvent(eventId, updatedEvent));
    }

    @Test
    void updateEvent_ShouldUpdateEventWithPartialData() {
        String eventId = "1";
        Event existingEvent = new Event();
        existingEvent.setId(eventId);
        existingEvent.setEventName("Ingresso pra cachoeira");
        existingEvent.setDateTime(LocalDateTime.parse("2025-03-22T21:30:00"));
        existingEvent.setCep("72980-970");

        Event updatedEvent = new Event();
        updatedEvent.setEventName("Ingresso pra cachoeira - Atualizado");

        Event updatedEventEntity = new Event();
        updatedEventEntity.setId(eventId);
        updatedEventEntity.setEventName("Ingresso pra cachoeira - Atualizado");
        updatedEventEntity.setDateTime(existingEvent.getDateTime());
        updatedEventEntity.setCep(existingEvent.getCep());

        when(eventRepository.findById(eventId)).thenReturn(Optional.of(existingEvent));
        when(eventRepository.save(updatedEventEntity)).thenReturn(updatedEventEntity);
        when(eventMapper.toUpdateEntity(existingEvent, updatedEvent)).thenReturn(updatedEventEntity);

        Event result = eventService.updateEvent(eventId, updatedEvent);

        assertEquals("Ingresso pra cachoeira - Atualizado", result.getEventName());
        assertEquals(existingEvent.getDateTime(), result.getDateTime());
        assertEquals(existingEvent.getCep(), result.getCep());

        verify(eventRepository, times(1)).findById(eventId);
        verify(eventMapper, times(1)).toUpdateEntity(existingEvent, updatedEvent);
        verify(eventRepository, times(1)).save(updatedEventEntity);
    }

    @Test
    void deleteEvent_ShouldDeleteEvent() {
        String eventId = "1";
        CheckTicketsResponseDTO response = new CheckTicketsResponseDTO();
        response.setHasTickets(false);

        when(eventRepository.existsById(eventId)).thenReturn(true);
        when(ticketClient.checkTicketsByEventId(eventId)).thenReturn(response);

        eventService.deleteEvent(eventId);

        verify(eventRepository, times(1)).deleteById(eventId);
    }

    @Test
    void deleteEvent_ShouldThrowEventNotFoundException() {
        String eventId = "1";

        when(eventRepository.existsById(eventId)).thenReturn(false);

        assertThrows(EventNotFoundException.class, () -> eventService.deleteEvent(eventId));
    }

    @Test
    void deleteEvent_ShouldThrowEventDeletionException() {
        String eventId = "1";
        CheckTicketsResponseDTO response = new CheckTicketsResponseDTO();
        response.setHasTickets(true);

        when(eventRepository.existsById(eventId)).thenReturn(true);
        when(ticketClient.checkTicketsByEventId(eventId)).thenReturn(response);

        assertThrows(EventDeletionException.class, () -> eventService.deleteEvent(eventId));
    }

    @Test
    void deleteEvent_ShouldThrowTicketServiceUnavailableException() {
        String eventId = "1";

        when(eventRepository.existsById(eventId)).thenReturn(true);
        when(ticketClient.checkTicketsByEventId(eventId)).thenThrow(new RuntimeException("Erro no cliente de tickets"));

        assertThrows(TicketServiceUnavailableException.class, () -> eventService.deleteEvent(eventId));
    }

    @Test
    void deleteEvent_ShouldDeleteEventWhenNoTicketsSold() {
        String eventId = "1";
        CheckTicketsResponseDTO response = new CheckTicketsResponseDTO();
        response.setHasTickets(false);

        when(eventRepository.existsById(eventId)).thenReturn(true);
        when(ticketClient.checkTicketsByEventId(eventId)).thenReturn(response);

        eventService.deleteEvent(eventId);

        verify(eventRepository, times(1)).deleteById(eventId);
    }
}