package com.compass.ms_event_manager.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "events")
@Schema(description = "Modelo de Evento")
public class Event {

    @Id
    private String id;

    @Schema(description = "Nome do evento", example = "Ingresso pra cachoeira")
    private String eventName;

    @Schema(description = "Data e hora do evento", example = "2025-03-22T21:30:00")
    private LocalDateTime dateTime;

    @Schema(description = "CEP do local do evento", example = "72980-970")
    private String cep;
    
    private String logradouro;
    private String bairro;
    private String cidade;
    private String uf;
}