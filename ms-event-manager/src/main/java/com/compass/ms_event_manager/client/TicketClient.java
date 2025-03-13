package com.compass.ms_event_manager.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.compass.ms_event_manager.dto.CheckTicketsResponseDTO;

@FeignClient(name = "ms-ticket-manager", url = "http://localhost:8081/api/v1/tickets")
public interface TicketClient {

    @GetMapping("/check-tickets-by-event/{eventId}")
    CheckTicketsResponseDTO checkTicketsByEventId(@PathVariable String eventId);
}