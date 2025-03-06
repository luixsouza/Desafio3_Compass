package com.compass.ms_ticket_manager.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "tickets")
public class Ticket {
    @Id
    private String ticketId;
    private String customerName;
    private String cpf;
    private String customerMail;
    private String eventId;
    private String eventName;
    private double BRLamount;
    private double USDamount;
    private String status;

    private String logradouro;
    private String bairro;
    private String cidade;
    private String uf;
}