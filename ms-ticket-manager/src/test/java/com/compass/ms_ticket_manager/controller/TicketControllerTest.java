package com.compass.ms_ticket_manager.controller;

import com.compass.ms_ticket_manager.dto.TicketDTO;
import com.compass.ms_ticket_manager.model.Ticket;
import com.compass.ms_ticket_manager.service.TicketService;
import com.compass.ms_ticket_manager.dto.CheckTicketsResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TicketControllerTest {

    @InjectMocks
    private TicketController ticketController;

    @Mock
    private TicketService ticketService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTicket_ShouldReturnCreatedTicket() {
        TicketDTO ticketDTO = new TicketDTO();
        ticketDTO.setCustomerName("Luis");
        ticketDTO.setCpf("999.653.645-36");
        ticketDTO.setCustomerMail("luis@email.com");
        ticketDTO.setEventId("67d1b3f03e28a3717a73784a");
        ticketDTO.setBrlAmount(new BigDecimal("50.00"));
        ticketDTO.setUsdAmount(new BigDecimal("7.50")); 

        Ticket ticket = new Ticket();
        when(ticketService.createTicket(any(TicketDTO.class))).thenReturn(ticket);

        ResponseEntity<Ticket> response = ticketController.createTicket(ticketDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(ticket, response.getBody());
    }

    @Test
    void getTicketById_ShouldReturnTicket() {
        Ticket ticket = new Ticket();
        when(ticketService.getTicketById("1")).thenReturn(ticket);

        ResponseEntity<Ticket> response = ticketController.getTicketById("1");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ticket, response.getBody());
    }

    @Test
    void getTicketsByCpf_ShouldReturnTickets() {
        List<Ticket> tickets = Arrays.asList(new Ticket(), new Ticket());
        when(ticketService.getTicketsByCpf("999.653.645-36")).thenReturn(tickets);

        ResponseEntity<List<Ticket>> response = ticketController.getTicketsByCpf("999.653.645-36");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tickets, response.getBody());
    }

    @Test
    void updateTicket_ShouldReturnUpdatedTicket() {
        TicketDTO ticketDTO = new TicketDTO();
        Ticket ticket = new Ticket();
        when(ticketService.updateTicket("1", ticketDTO)).thenReturn(ticket);

        ResponseEntity<Ticket> response = ticketController.updateTicket("1", ticketDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ticket, response.getBody());
    }

    @Test
    void checkTicketsByEventId_ShouldReturnCheckTicketsResponseDTO() {
        when(ticketService.hasTicketsByEventId("67d1b3f03e28a3717a73784a")).thenReturn(true);

        ResponseEntity<CheckTicketsResponseDTO> response = ticketController.checkTicketsByEventId("67d1b3f03e28a3717a73784a");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody().isHasTickets());
    }

    @Test
    void cancelTicketById_ShouldReturnNoContent() {
        Ticket ticket = new Ticket();
        ticket.setTicketId("1");
        ticket.setStatus("ativo");

        when(ticketService.cancelTicketById("1")).thenReturn(ticket);

        ResponseEntity<Void> response = ticketController.cancelTicketById("1");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(ticketService, times(1)).cancelTicketById("1");
    }

    @Test
    void cancelTicketsByCpf_ShouldReturnNoContent() {
        Ticket ticket1 = new Ticket();
        ticket1.setTicketId("1");
        ticket1.setCpf("999.653.645-36");
        ticket1.setStatus("concluído");

        Ticket ticket2 = new Ticket();
        ticket2.setTicketId("2");
        ticket2.setCpf("999.653.645-36");
        ticket2.setStatus("concluído");

        List<Ticket> tickets = Arrays.asList(ticket1, ticket2);

        when(ticketService.cancelTicketsByCpf("999.653.645-36")).thenReturn(tickets);

        ResponseEntity<Void> response = ticketController.cancelTicketsByCpf("999.653.645-36");

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

        verify(ticketService, times(1)).cancelTicketsByCpf("999.653.645-36");
    }
}