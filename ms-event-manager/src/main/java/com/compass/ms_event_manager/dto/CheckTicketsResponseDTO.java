package com.compass.ms_event_manager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckTicketsResponseDTO {
    private String eventId;
    private boolean hasTickets;
}