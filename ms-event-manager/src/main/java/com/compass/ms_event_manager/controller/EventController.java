package com.compass.ms_event_manager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.compass.ms_event_manager.model.Event;
import com.compass.ms_event_manager.service.EventService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventController {
    private final EventService eventService;

    @PostMapping("/create-event")
    public Event createEvent(@RequestBody Event event) {
        return eventService.createEvent(event);
    }

    @GetMapping("/get-event/{id}")
    public Optional<Event> getEventById(@PathVariable String id) {
        return eventService.getEventById(id);
    }

    @GetMapping("/get-all-events")
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @DeleteMapping("/delete-event/{id}")
    public String deleteEvent(@PathVariable String id) {
        eventService.deleteEvent(id);
        return "Evento removido com sucesso!";
    }
}

