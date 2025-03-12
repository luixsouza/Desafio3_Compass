package com.compass.ms_ticket_manager.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "tickets")
public class Ticket {

    @Id
    private String ticketId;
    private String cpf;
    private String customerName;
    private String customerMail;
    private BigDecimal brlAmount;
    private BigDecimal usdAmount;
    private String status;
    private Event event;
}