package com.compass.ms_event_manager.mapper;

import com.compass.ms_event_manager.client.ViaCepClient;
import com.compass.ms_event_manager.model.Event;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventMapper {

    private final ViaCepClient viaCepClient;

    public Event toCreateEntity(Event event) {
        var endereco = viaCepClient.getAddressByCep(event.getCep());
        return Event.builder()
                .eventName(event.getEventName())
                .dateTime(event.getDateTime())
                .cep(event.getCep())
                .logradouro(endereco.getLogradouro())
                .bairro(endereco.getBairro())
                .cidade(endereco.getLocalidade())
                .uf(endereco.getUf())
                .build();
    }

    public Event toUpdateEntity(Event existingEvent, Event updatedEvent) {
        var endereco = viaCepClient.getAddressByCep(updatedEvent.getCep());
        return Event.builder()
                .id(existingEvent.getId())
                .eventName(updatedEvent.getEventName() != null ? updatedEvent.getEventName() : existingEvent.getEventName())
                .dateTime(updatedEvent.getDateTime() != null ? updatedEvent.getDateTime() : existingEvent.getDateTime())
                .cep(updatedEvent.getCep() != null ? updatedEvent.getCep() : existingEvent.getCep())
                .logradouro(updatedEvent.getCep() != null ? endereco.getLogradouro() : existingEvent.getLogradouro())
                .bairro(updatedEvent.getCep() != null ? endereco.getBairro() : existingEvent.getBairro())
                .cidade(updatedEvent.getCep() != null ? endereco.getLocalidade() : existingEvent.getCidade())
                .uf(updatedEvent.getCep() != null ? endereco.getUf() : existingEvent.getUf())
                .build();
    }
}