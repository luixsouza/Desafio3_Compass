package com.compass.ms_ticket_manager.service;

import org.springframework.stereotype.Service;
import com.compass.ms_ticket_manager.client.EventClient;
import com.compass.ms_ticket_manager.dto.TicketDTO;
import com.compass.ms_ticket_manager.exception.EventNotFoundException;
import com.compass.ms_ticket_manager.exception.NoTicketsFoundForEventException;
import com.compass.ms_ticket_manager.exception.TicketNotFoundException;
import com.compass.ms_ticket_manager.exception.TicketsNotFoundCpfException;
import com.compass.ms_ticket_manager.mapper.TicketMapper;
import com.compass.ms_ticket_manager.model.Event;
import com.compass.ms_ticket_manager.model.Ticket;
import com.compass.ms_ticket_manager.repository.TicketRepository;
import com.compass.ms_ticket_manager.validator.TicketValidator;

import lombok.AllArgsConstructor;
import java.util.List;

@Service
@AllArgsConstructor
public class TicketService {

    private final EventClient eventClient;
    private final TicketRepository ticketRepository;
    private final TicketMapper ticketMapper;
    private final TicketValidator ticketValidator;

    public Ticket createTicket(TicketDTO ticketDTO) {
        Event event = eventClient.getEventById(ticketDTO.getEventId());
        if (event == null) {
            throw new EventNotFoundException("Evento não encontrado");
        }
        Ticket ticket = ticketMapper.toEntity(ticketDTO, event);
        return ticketRepository.save(ticket);
    }

    public Ticket getTicketById(String id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException("Ingresso não encontrado"));
    }

    public List<Ticket> getTicketsByCpf(String cpf) {
        List<Ticket> tickets = ticketRepository.findByCpf(cpf);
        if (tickets.isEmpty()) {
            throw new TicketsNotFoundCpfException("Nenhum ingresso encontrado para o CPF: " + cpf);
        }
        return tickets;
    }

    public Ticket updateTicket(String id, TicketDTO ticketDTO) {
        Ticket existingTicket = getTicketById(id);
        ticketValidator.validateTicketNotCancelled(existingTicket);
        Ticket updatedTicket = ticketMapper.toEntity(ticketDTO, existingTicket);
        return ticketRepository.save(updatedTicket);
    }

    public boolean hasTicketsByEventId(String eventId) {
        boolean hasTickets = ticketRepository.existsByEventIdAndStatus(eventId, "concluído");
        if (!hasTickets) {
            throw new NoTicketsFoundForEventException("Nenhum ingresso concluído encontrado para o evento: " + eventId);
        }
        return true;
    }

    public Ticket cancelTicketById(String id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new TicketNotFoundException("Ingresso não encontrado"));
        ticket.setStatus("cancelado");
        return ticketRepository.save(ticket);
    }

    public List<Ticket> cancelTicketsByCpf(String cpf) {
        List<Ticket> tickets = ticketRepository.findByCpf(cpf);
        if (tickets.isEmpty()) {
            throw new TicketsNotFoundCpfException("Nenhum ingresso encontrado para o CPF: " + cpf);
        }
        tickets.forEach(ticket -> ticket.setStatus("cancelado"));
        return ticketRepository.saveAll(tickets);
    }
}