package com.compass.ms_ticket_manager.service;

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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TicketServiceTest {

    @InjectMocks
    private TicketService ticketService;

    @Mock
    private EventClient eventClient;

    @Mock
    private TicketRepository ticketRepository;

    @Mock
    private TicketMapper ticketMapper;

    @Mock
    private TicketValidator ticketValidator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTicket_ShouldReturnCreatedTicket() {
        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setEventId("67d1b3f03e28a3717a73784a");

        Event event = new Event();
        when(eventClient.getEventById("67d1b3f03e28a3717a73784a")).thenReturn(event);

        Ticket ticket = new Ticket();
        when(ticketMapper.toEntity(ticketDTO, event)).thenReturn(ticket);
        when(ticketRepository.save(ticket)).thenReturn(ticket);

        Ticket createdTicket = ticketService.createTicket(ticketDTO);

        assertEquals(ticket, createdTicket);
    }

    @Test
    void createTicket_ShouldThrowEventNotFoundException() {
        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setEventId("67d1b3f03e28a3717a73784a");

        when(eventClient.getEventById("67d1b3f03e28a3717a73784a")).thenReturn(null);

        assertThrows(EventNotFoundException.class, () -> ticketService.createTicket(ticketDTO));
    }

    @Test
    void getTicketById_ShouldReturnTicket() {
        Ticket ticket = new Ticket();
        when(ticketRepository.findById("1")).thenReturn(Optional.of(ticket));

        Ticket foundTicket = ticketService.getTicketById("1");

        assertEquals(ticket, foundTicket);
    }

    @Test
    void getTicketById_ShouldThrowTicketNotFoundException() {
        when(ticketRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(TicketNotFoundException.class, () -> ticketService.getTicketById("1"));
    }

    @Test
    void getTicketsByCpf_ShouldReturnTickets() {
        List<Ticket> tickets = Arrays.asList(new Ticket(), new Ticket());
        when(ticketRepository.findByCpf("999.653.645-36")).thenReturn(tickets);

        List<Ticket> foundTickets = ticketService.getTicketsByCpf("999.653.645-36");

        assertEquals(tickets, foundTickets);
    }

    @Test
    void getTicketsByCpf_ShouldThrowTicketsNotFoundCpfException() {
        when(ticketRepository.findByCpf("999.653.645-36")).thenReturn(Collections.emptyList());

        assertThrows(TicketsNotFoundCpfException.class, () -> ticketService.getTicketsByCpf("999.653.645-36"));
    }

    @Test
    void updateTicket_ShouldReturnUpdatedTicket() {
        TicketDTO ticketDTO = new TicketDTO();
        Ticket existingTicket = new Ticket();
        when(ticketRepository.findById("1")).thenReturn(Optional.of(existingTicket));
        when(ticketMapper.toEntity(ticketDTO, existingTicket)).thenReturn(existingTicket);
        when(ticketRepository.save(existingTicket)).thenReturn(existingTicket);

        Ticket updatedTicket = ticketService.updateTicket("1", ticketDTO);

        assertEquals(existingTicket, updatedTicket);
    }

    @Test
    void updateTicket_ShouldThrowTicketNotFoundException() {
        TicketDTO ticketDTO = new TicketDTO();
        when(ticketRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(TicketNotFoundException.class, () -> ticketService.updateTicket("1", ticketDTO));
    }

    @Test
    void hasTicketsByEventId_ShouldReturnTrue() {
        when(ticketRepository.existsByEventIdAndStatus("67d1b3f03e28a3717a73784a", "concluído")).thenReturn(true);

        boolean hasTickets = ticketService.hasTicketsByEventId("67d1b3f03e28a3717a73784a");

        assertTrue(hasTickets);
    }

    @Test
    void hasTicketsByEventId_ShouldThrowNoTicketsFoundForEventException() {
        when(ticketRepository.existsByEventIdAndStatus("67d1b3f03e28a3717a73784a", "concluído")).thenReturn(false);

        assertThrows(NoTicketsFoundForEventException.class, () -> ticketService.hasTicketsByEventId("67d1b3f03e28a3717a73784a"));
    }

    @Test
    void cancelTicketById_ShouldReturnCanceledTicket() {
        Ticket ticket = new Ticket();
        ticket.setStatus("ativo");
        when(ticketRepository.findById("1")).thenReturn(Optional.of(ticket));
        when(ticketRepository.save(ticket)).thenReturn(ticket);

        Ticket canceledTicket = ticketService.cancelTicketById("1");

        assertEquals("cancelado", canceledTicket.getStatus());
    }

    @Test
    void cancelTicketById_ShouldThrowTicketNotFoundException() {
        when(ticketRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(TicketNotFoundException.class, () -> ticketService.cancelTicketById("1"));
    }

    @Test
    void cancelTicketsByCpf_ShouldReturnCanceledTickets() {
        Ticket ticket1 = new Ticket();
        ticket1.setStatus("ativo");
        Ticket ticket2 = new Ticket();
        ticket2.setStatus("ativo");
        List<Ticket> tickets = Arrays.asList(ticket1, ticket2);
        when(ticketRepository.findByCpf("999.653.645-36")).thenReturn(tickets);
        when(ticketRepository.saveAll(tickets)).thenReturn(tickets);

        List<Ticket> canceledTickets = ticketService.cancelTicketsByCpf("999.653.645-36");

        assertEquals("cancelado", canceledTickets.get(0).getStatus());
        assertEquals("cancelado", canceledTickets.get(1).getStatus());
    }

    @Test
    void cancelTicketsByCpf_ShouldThrowTicketsNotFoundCpfException() {
        when(ticketRepository.findByCpf("999.653.645-36")).thenReturn(Collections.emptyList());

        assertThrows(TicketsNotFoundCpfException.class, () -> ticketService.cancelTicketsByCpf("999.653.645-36"));
    }

    @Test
    void validateTicketNotCancelled_ShouldNotThrowException() {
        Ticket ticket = new Ticket();
        ticket.setStatus("ativo");

        doNothing().when(ticketValidator).validateTicketNotCancelled(ticket);

        assertDoesNotThrow(() -> ticketValidator.validateTicketNotCancelled(ticket));
    }

    @Test
    void validateTicketNotCancelled_ShouldThrowException() {
        Ticket ticket = new Ticket();
        ticket.setStatus("cancelado");

        doThrow(new IllegalArgumentException("Ingresso já cancelado")).when(ticketValidator).validateTicketNotCancelled(ticket);

        assertThrows(IllegalArgumentException.class, () -> ticketValidator.validateTicketNotCancelled(ticket));
    }
}