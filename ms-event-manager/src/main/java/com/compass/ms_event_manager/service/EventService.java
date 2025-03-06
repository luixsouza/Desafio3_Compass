package com.compass.ms_event_manager.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.compass.ms_event_manager.client.ViaCepClient;
import com.compass.ms_event_manager.model.Event;
import com.compass.ms_event_manager.repository.EventRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventService {
    private final EventRepository eventRepository;
    private final ViaCepClient viaCepClient;

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

    public void deleteEvent(String id) {
        eventRepository.deleteById(id);
    }
}