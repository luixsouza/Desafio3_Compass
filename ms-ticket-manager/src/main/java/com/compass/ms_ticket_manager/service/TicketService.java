package com.compass.ms_ticket_manager.service;

import org.springframework.stereotype.Service;

import com.compass.ms_ticket_manager.client.EventClient;
import com.compass.ms_ticket_manager.dto.TicketDTO;
import com.compass.ms_ticket_manager.model.Event;
import com.compass.ms_ticket_manager.model.Ticket;
import com.compass.ms_ticket_manager.repository.TicketRepository;

import lombok.AllArgsConstructor;

import java.util.UUID;

@Service
@AllArgsConstructor
public class TicketService {

    private final TicketRepository ticketRepository;
    private final EventClient eventClient;

    public Ticket createTicket(TicketDTO ticketDTO) {

        Event event = eventClient.getEventById(ticketDTO.getEventId());
        if (event == null) {
            throw new RuntimeException("Evento não encontrado");
        }

        Ticket ticket = new Ticket();
        ticket.setTicketId(UUID.randomUUID().toString());
        ticket.setCpf(ticketDTO.getCpf());
        ticket.setCustomerName(ticketDTO.getCustomerName());
        ticket.setCustomerMail(ticketDTO.getCustomerMail());
        ticket.setStatus("concluído");
        ticket.setEvent(event);

        return ticketRepository.save(ticket);
    } 
}