package com.compass.ms_ticket_manager.dto;

import lombok.Data;

@Data
public class TicketDTO {

    private String cpf;
    private String customerName;
    private String customerMail;
    private String eventId;
    private String eventName;
    private String brlAmount;
    private String usdAmount;
}