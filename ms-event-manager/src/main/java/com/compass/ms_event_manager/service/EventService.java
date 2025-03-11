package com.compass.ms_event_manager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.compass.ms_event_manager.client.TicketClient;
import com.compass.ms_event_manager.client.ViaCepClient;
import com.compass.ms_event_manager.dto.CheckTicketsResponseDTO;
import com.compass.ms_event_manager.exception.EventDeletionException;
import com.compass.ms_event_manager.model.Event;
import com.compass.ms_event_manager.repository.EventRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final ViaCepClient viaCepClient;
    private final TicketClient ticketClient;

    public Event createEvent(Event event) {
        var endereco = viaCepClient.getAddressByCep(event.getCep());
        event.setLogradouro(endereco.getLogradouro());
        event.setBairro(endereco.getBairro());
        event.setCidade(endereco.getLocalidade());
        event.setUf(endereco.getUf());

        return eventRepository.save(event);
    }

    public Optional<Event> getEventById(String id) {
        return eventRepository.findById(id);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public void deleteEvent(String eventId) {
        CheckTicketsResponseDTO response = ticketClient.checkTicketsByEventId(eventId);
        if (response.isHasTickets()) {
            throw new EventDeletionException("O evento não pode ser deletado porque possui ingressos vendidos.");
        }
        eventRepository.deleteById(eventId);
    }

    public List<Event> getAllEventsSorted() {
        return eventRepository.findAllByOrderByEventNameAsc();
    }

    public Event updateEvent(String id, Event updatedEvent) {
        Event existingEvent = eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Evento não encontrado com o ID: " + id));

        existingEvent.setEventName(updatedEvent.getEventName());
        existingEvent.setDateTime(updatedEvent.getDateTime());
        existingEvent.setCep(updatedEvent.getCep());

        var endereco = viaCepClient.getAddressByCep(updatedEvent.getCep());
        existingEvent.setLogradouro(endereco.getLogradouro());
        existingEvent.setBairro(endereco.getBairro());
        existingEvent.setCidade(endereco.getLocalidade());
        existingEvent.setUf(endereco.getUf());

        return eventRepository.save(existingEvent);
    }   
}