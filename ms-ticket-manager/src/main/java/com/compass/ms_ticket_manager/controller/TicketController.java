package com.compass.ms_ticket_manager.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.compass.ms_ticket_manager.dto.CheckTicketsResponseDTO;
import com.compass.ms_ticket_manager.dto.TicketDTO;
import com.compass.ms_ticket_manager.model.Ticket;
import com.compass.ms_ticket_manager.service.TicketService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @Operation(
        summary = "Cria um novo ingresso",
        description = "Esse endpoint cria um novo ingresso no sistema para um evento específico.",
        responses = {
            @ApiResponse(
                responseCode = "201", 
                description = "Ingresso criado com sucesso", 
                content = @Content(schema = @Schema(implementation = Ticket.class))
            ),
            @ApiResponse(responseCode = "400", description = "Dados do ingresso inválidos ou incompletos."),
            @ApiResponse(responseCode = "404", description = "Evento não encontrado")
        }
    )
    @PostMapping("/create-ticket")
    public ResponseEntity<Ticket> createTicket(@RequestBody TicketDTO ticketDTO) {
        Ticket createdTicket = ticketService.createTicket(ticketDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdTicket);
    }

    @Operation(
        summary = "Obtém um ingresso pelo ID",
        description = "Esse endpoint recupera os detalhes de um ingresso utilizando seu ID.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Ingresso encontrado", content = @Content(schema = @Schema(implementation = Ticket.class))),
            @ApiResponse(responseCode = "404", description = "Ingresso não encontrado")
        }
    )
    @GetMapping("/get-ticket/{id}")
    public ResponseEntity<Ticket> getTicketById(@PathVariable String id) {
        Ticket ticket = ticketService.getTicketById(id);
        return ResponseEntity.status(HttpStatus.OK).body(ticket);
    }

    @Operation(
        summary = "Obtém todos os ingressos de um CPF",
        description = "Esse endpoint recupera todos os ingressos associados ao CPF informado.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Ingressos encontrados", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Ticket.class)))),
            @ApiResponse(responseCode = "404", description = "Nenhum ingresso encontrado para o CPF")
        }
    )
    @GetMapping("/get-ticket-by-cpf/{cpf}")
    public ResponseEntity<List<Ticket>> getTicketsByCpf(@PathVariable String cpf) {
        List<Ticket> tickets = ticketService.getTicketsByCpf(cpf);
        return ResponseEntity.status(HttpStatus.OK).body(tickets);
    }

    @Operation(
        summary = "Atualiza um ingresso",
        description = "Esse endpoint atualiza as informações de um ingresso existente.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Ingresso atualizado com sucesso", content = @Content(schema = @Schema(implementation = Ticket.class))),
            @ApiResponse(responseCode = "400", description = "Ingresso cancelado não pode ser atualizado"),
            @ApiResponse(responseCode = "404", description = "Ingresso não encontrado")
        }
    )
    @PatchMapping("/update-ticket/{id}")
    public ResponseEntity<Ticket> updateTicket(@PathVariable String id, @RequestBody TicketDTO ticketDTO) {
        Ticket updatedTicket = ticketService.updateTicket(id, ticketDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedTicket);
    }

    @Operation(
        summary = "Verifica se há ingressos para um evento",
        description = "Esse endpoint verifica se existem ingressos para o evento informado.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Resposta com status dos ingressos", content = @Content(schema = @Schema(implementation = CheckTicketsResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Nenhum ingresso encontrado para o evento")
        }
    )
    @GetMapping("/check-tickets-by-event/{eventId}")
    public ResponseEntity<CheckTicketsResponseDTO> checkTicketsByEventId(@PathVariable String eventId) {
        boolean hasTickets = ticketService.hasTicketsByEventId(eventId);
        CheckTicketsResponseDTO response = new CheckTicketsResponseDTO(eventId, hasTickets);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Operation(
        summary = "Cancela um ingresso",
        description = "Esse endpoint cancela o ingresso identificado pelo ID.",
        responses = {
            @ApiResponse(responseCode = "204", description = "Ingresso cancelado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Ingresso não encontrado")
        }
    )
    @DeleteMapping("/cancel-ticket/{id}")
    public ResponseEntity<Void> cancelTicketById(@PathVariable String id) {
        ticketService.cancelTicketById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(
        summary = "Cancela todos os ingressos de um CPF",
        description = "Esse endpoint cancela todos os ingressos associados ao CPF informado.",
        responses = {
            @ApiResponse(responseCode = "204", description = "Ingressos cancelados com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhum ingresso encontrado para o CPF")
        }
    )
    @DeleteMapping("/cancel-ticket-by-cpf/{cpf}")
    public ResponseEntity<Void> cancelTicketsByCpf(@PathVariable String cpf) {
        ticketService.cancelTicketsByCpf(cpf);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}