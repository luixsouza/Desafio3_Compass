package com.compass.ms_event_manager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CheckTicketsResponseDTO {
    private String eventId;
    private boolean hasTickets;
}