package com.compass.ms_ticket_manager.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.compass.ms_ticket_manager.dto.CheckTicketsResponseDTO;
import com.compass.ms_ticket_manager.dto.TicketDTO;
import com.compass.ms_ticket_manager.model.Ticket;
import com.compass.ms_ticket_manager.service.TicketService;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/create-ticket")
    public ResponseEntity<Ticket> createTicket(@RequestBody TicketDTO ticketDTO) {
        Ticket createdTicket = ticketService.createTicket(ticketDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTicket);
    }

    @GetMapping("/get-ticket/{id}")
    public Ticket getTicketById(@PathVariable String id) {
        return ticketService.getTicketById(id);
    }

    @GetMapping("/get-ticket-by-cpf/{cpf}")
    public List<Ticket> getTicketsByCpf(@PathVariable String cpf) {
        return ticketService.getTicketsByCpf(cpf);
    }

    @PatchMapping("/update-ticket/{id}")
    public ResponseEntity<Ticket> updateTicket(@PathVariable String id, @RequestBody TicketDTO ticketDTO) {
        Ticket updatedTicket = ticketService.updateTicket(id, ticketDTO);
        return ResponseEntity.ok(updatedTicket);
    }

    @GetMapping("/check-tickets-by-event/{eventId}")
    public ResponseEntity<CheckTicketsResponseDTO> checkTicketsByEventId(@PathVariable String eventId) {
        boolean hasTickets = ticketService.hasTicketsByEventId(eventId);
        CheckTicketsResponseDTO response = new CheckTicketsResponseDTO(eventId, hasTickets);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/cancel-ticket/{id}")
    public Ticket cancelTicketById(@PathVariable String id) {
        return ticketService.cancelTicketById(id);
    }

    @DeleteMapping("/cancel-ticket-by-cpf/{cpf}")
    public List<Ticket> cancelTicketsByCpf(@PathVariable String cpf) {
        return ticketService.cancelTicketsByCpf(cpf);
    }
}