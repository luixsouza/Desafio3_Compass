package com.compass.ms_ticket_manager.service;

import org.springframework.stereotype.Service;
import com.compass.ms_ticket_manager.client.EventClient;
import com.compass.ms_ticket_manager.dto.TicketDTO;
import com.compass.ms_ticket_manager.model.Event;
import com.compass.ms_ticket_manager.model.Ticket;
import com.compass.ms_ticket_manager.repository.TicketRepository;
import lombok.AllArgsConstructor;
import java.util.List;
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

    public Ticket getTicketById(String id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingresso não encontrado"));
    }

    public List<Ticket> getTicketsByCpf(String cpf) {
        return ticketRepository.findByCpf(cpf);
    }

    public Ticket cancelTicketById(String id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ingresso não encontrado"));
        ticket.setStatus("cancelado");
        return ticketRepository.save(ticket);
    }

    public List<Ticket> cancelTicketsByCpf(String cpf) {
        List<Ticket> tickets = ticketRepository.findByCpf(cpf);
        tickets.forEach(ticket -> ticket.setStatus("cancelado"));
        return ticketRepository.saveAll(tickets);
    }
}