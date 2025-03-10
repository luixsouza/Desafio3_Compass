package com.compass.ms_ticket_manager.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.compass.ms_ticket_manager.dto.TicketDTO;
import com.compass.ms_ticket_manager.model.Ticket;
import com.compass.ms_ticket_manager.service.TicketService;

@RestController
@RequestMapping("/api/v1/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @PostMapping("/create-ticket")
    public ResponseEntity<Object> createTicket(@RequestBody TicketDTO ticketDTO) {
        Logger logger = LoggerFactory.getLogger(this.getClass());
    
        try {
            Ticket ticket = ticketService.createTicket(ticketDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(ticket);
        } catch (Exception e) {
            logger.error("Erro ao criar ticket: ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/get-ticket/{id}")
    public Ticket getTicketById(@PathVariable String id) {
        return ticketService.getTicketById(id);
    }

    @GetMapping("/get-ticket-by-cpf/{cpf}")
    public List<Ticket> getTicketsByCpf(@PathVariable String cpf) {
        return ticketService.getTicketsByCpf(cpf);
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