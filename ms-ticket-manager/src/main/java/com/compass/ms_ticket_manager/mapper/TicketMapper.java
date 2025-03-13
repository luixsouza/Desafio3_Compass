package com.compass.ms_ticket_manager.mapper;

import com.compass.ms_ticket_manager.dto.TicketDTO;
import com.compass.ms_ticket_manager.model.Event;
import com.compass.ms_ticket_manager.model.Ticket;

import java.util.UUID;

import org.springframework.stereotype.Component;

@Component
public class TicketMapper {

    public Ticket toEntity(TicketDTO ticketDTO, Event event) {
        return Ticket.builder()
                .ticketId(UUID.randomUUID().toString())
                .cpf(ticketDTO.getCpf())
                .customerName(ticketDTO.getCustomerName())
                .customerMail(ticketDTO.getCustomerMail())
                .brlAmount(ticketDTO.getBrlAmount())
                .usdAmount(ticketDTO.getUsdAmount())
                .status("concluído")
                .event(event)
                .build();
    }

    public Ticket toEntity(TicketDTO ticketDTO, Ticket existingTicket) {
        return Ticket.builder()
                .ticketId(existingTicket.getTicketId())
                .cpf(existingTicket.getCpf())
                .customerName(ticketDTO.getCustomerName() != null ? ticketDTO.getCustomerName() : existingTicket.getCustomerName())
                .customerMail(ticketDTO.getCustomerMail() != null ? ticketDTO.getCustomerMail() : existingTicket.getCustomerMail())
                .brlAmount(ticketDTO.getBrlAmount() != null ? ticketDTO.getBrlAmount() : existingTicket.getBrlAmount())
                .usdAmount(ticketDTO.getUsdAmount() != null ? ticketDTO.getUsdAmount() : existingTicket.getUsdAmount())
                .status(existingTicket.getStatus())
                .event(existingTicket.getEvent())
                .build();
    }
}