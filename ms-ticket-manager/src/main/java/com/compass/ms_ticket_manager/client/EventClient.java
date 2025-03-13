package com.compass.ms_ticket_manager.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.compass.ms_ticket_manager.model.Event;

@FeignClient(name = "ms-event-manager", url = "http://localhost:8080/api/v1/events")
public interface EventClient {

    @GetMapping("/get-event/{eventId}")
    Event getEventById(@PathVariable("eventId") String eventId);
}