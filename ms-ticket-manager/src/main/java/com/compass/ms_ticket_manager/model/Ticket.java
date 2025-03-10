package com.compass.ms_ticket_manager.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Getter
@Setter
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