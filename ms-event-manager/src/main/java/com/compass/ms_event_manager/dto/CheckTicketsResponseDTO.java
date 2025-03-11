package com.compass.ms_event_manager.dto;

import lombok.Data;

@Data
public class CheckTicketsResponseDTO {
    private String eventId;
    private boolean hasTickets;

    public CheckTicketsResponseDTO(String eventId, boolean hasTickets) {
        this.eventId = eventId;
        this.hasTickets = hasTickets;
    }
}