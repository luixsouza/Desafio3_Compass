package com.compass.ms_event_manager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.compass.ms_event_manager.client.TicketClient;
import com.compass.ms_event_manager.dto.CheckTicketsResponseDTO;
import com.compass.ms_event_manager.exception.*;
import com.compass.ms_event_manager.mapper.EventMapper;
import com.compass.ms_event_manager.model.Event;
import com.compass.ms_event_manager.repository.EventRepository;

import feign.FeignException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final TicketClient ticketClient;
    private final EventMapper eventMapper;

    public Event createEvent(Event event) {
        if (event == null || event.getEventName() == null || event.getDateTime() == null) {
            throw new InvalidEventDataException("Dados do evento inválidos ou incompletos.");
        }
        try {
            Event eventEntity = eventMapper.toCreateEntity(event);
            return eventRepository.save(eventEntity);
        } catch (Exception e) {
            throw new EventCreationException("Erro ao criar o evento: " + e.getMessage());
        }
    }

    public Event getEventById(String id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new EventNotFoundException("Evento não encontrado com ID: " + id));
    }
    

    public List<Event> getAllEvents() {
        try {
            return eventRepository.findAll();
        } catch (Exception e) {
            throw new EventRetrievalException("Erro ao recuperar os eventos: " + e.getMessage());
        }
    }

    public void deleteEvent(String eventId) {
        if (!eventRepository.existsById(eventId)) {
            throw new EventNotFoundException("Evento não encontrado para o id: " + eventId);
        } try {
            CheckTicketsResponseDTO response = ticketClient.checkTicketsByEventId(eventId);
            if (response.isHasTickets()) {
                throw new EventDeletionException("O evento não pode ser deletado porque possui ingressos vendidos.");
            }
            eventRepository.deleteById(eventId);
        } catch (FeignException.NotFound e) {
            eventRepository.deleteById(eventId);
        } catch (FeignException e) {
            throw new TicketServiceUnavailableException("Erro ao se comunicar com o serviço de tickets: " + e.getMessage());
        } catch (EventDeletionException e) {
            throw e;
        } catch (Exception e) {
            throw new TicketServiceUnavailableException("Erro inesperado: " + e.getMessage());
        }
    }
    
    public List<Event> getAllEventsSorted() {
        try {
            return eventRepository.findAllByOrderByEventNameAsc();
        } catch (Exception e) {
            throw new EventRetrievalException("Erro ao recuperar os eventos ordenados: " + e.getMessage());
        }
    }

    public Event updateEvent(String eventId, Event updatedEvent) {
        Event existingEvent = eventRepository.findById(eventId)
                .orElseThrow(() -> new EventNotFoundException("Evento não encontrado com ID: " + eventId));
        if (updatedEvent == null || (updatedEvent.getEventName() == null && updatedEvent.getDateTime() == null)) {
            throw new InvalidEventDataException("Pelo menos um campo deve ser fornecido para atualização.");
        } try {
            Event updatedEventEntity = eventMapper.toUpdateEntity(existingEvent, updatedEvent);
            return eventRepository.save(updatedEventEntity);
        } catch (Exception e) {
            throw new EventUpdateException("Erro ao atualizar o evento: " + e.getMessage());
        }
    }
}