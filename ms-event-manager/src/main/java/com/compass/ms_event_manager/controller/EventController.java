package com.compass.ms_event_manager.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.compass.ms_event_manager.model.Event;
import com.compass.ms_event_manager.service.EventService;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventController {
    
    private final EventService eventService;

    @Operation(
        summary = "Cria um novo evento",
        description = "Esse Endpoint cria um novo evento no sistema.",
        responses = {
            @ApiResponse(
                responseCode = "201", 
                description = "Evento criado com sucesso", 
                content = @Content(schema = @Schema(implementation = Event.class))
            ),
            @ApiResponse(responseCode = "400", description = "Dados do evento inválidos ou incompletos.")
        }
    )
    @PostMapping("/create-event")
    public ResponseEntity<Event> createEvent(
        @Parameter(description = "Dados do evento a ser criado", 
                   content = @Content(
                       schema = @Schema(implementation = Event.class, 
                                       example = "{ \"eventName\": \"Ingresso pra cachoeira\", \"dateTime\": \"2025-03-22T21:30:00\", \"cep\": \"72980-970\" }")
                   )
        ) 
        @RequestBody Event event) {
        Event createdEvent = eventService.createEvent(event);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEvent);
    }

    @Operation(
        summary = "Obtém um evento pelo ID",
        description = "Esse Endpoint retorna um evento com base no ID fornecido.",
        responses = {
            @ApiResponse(
                responseCode = "200", 
                description = "Evento encontrado", 
                content = @Content(schema = @Schema(implementation = Event.class))
            ),
            @ApiResponse(responseCode = "404", description = "Evento não encontrado")
        }
    )
    @GetMapping("/get-event/{id}")
    public ResponseEntity<Event> getEventById(
        @Parameter(description = "ID do evento a ser buscado") 
        @PathVariable String id) {
        Event event = eventService.getEventById(id);
        return ResponseEntity.ok(event);
    }

    @Operation(
        summary = "Obtém todos os eventos",
        description = "Esse Endpoint retorna uma lista de todos os eventos.",
        responses = {
            @ApiResponse(
                responseCode = "200", 
                description = "Eventos encontrados", 
                content = @Content(schema = @Schema(implementation = Event.class))
            )
        }
    )
    @GetMapping("/get-all-events")
    public ResponseEntity<List<Event>> getAllEvents() {
        List<Event> events = eventService.getAllEvents();
        return ResponseEntity.ok(events);
    }

    @Operation(
        summary = "Obtém todos os eventos ordenados",
        description = "Esse Endpoint retorna uma lista de eventos ordenados.",
        responses = {
            @ApiResponse(
                responseCode = "200", 
                description = "Eventos ordenados encontrados", 
                content = @Content(schema = @Schema(implementation = Event.class))
            )
        }
    )
    @GetMapping("/get-all-events/sorted")
    public ResponseEntity<List<Event>> getAllEventsSorted() {
        List<Event> sortedEvents = eventService.getAllEventsSorted();
        return ResponseEntity.ok(sortedEvents);
    }

    @Operation(
        summary = "Atualiza um evento existente",
        description = "Esse Endpoint atualiza um evento com base no ID fornecido e nos novos dados.",
        responses = {
            @ApiResponse(
                responseCode = "200", 
                description = "Evento atualizado com sucesso", 
                content = @Content(schema = @Schema(implementation = Event.class))
            ),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos"),
            @ApiResponse(responseCode = "404", description = "Evento não encontrado")
        }
    )
    @PutMapping("/update-event/{id}")
    public ResponseEntity<Event> updateEvent(
        @Parameter(description = "ID do evento a ser atualizado") 
        @PathVariable String id, 
        @RequestBody Event updatedEvent) {
        Event event = eventService.updateEvent(id, updatedEvent);
        return ResponseEntity.ok(event);
    }

    @Operation(
        summary = "Deleta um evento",
        description = "Esse Endpoint deleta um evento do sistema com base no ID fornecido.",
        responses = {
            @ApiResponse(responseCode = "204", description = "Evento deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Evento não encontrado"),
            @ApiResponse(responseCode = "409", description = "O evento não pode ser deletado porque possui ingressos vendidos.")
        }
    )
    @DeleteMapping("/delete-event/{eventId}")
    public ResponseEntity<Void> deleteEvent(
        @Parameter(description = "ID do evento a ser deletado") 
        @PathVariable String eventId) {
        eventService.deleteEvent(eventId);
        return ResponseEntity.noContent().build();
    }
}