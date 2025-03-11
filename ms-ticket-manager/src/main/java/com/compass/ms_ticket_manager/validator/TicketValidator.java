package com.compass.ms_ticket_manager.validator;

import com.compass.ms_ticket_manager.exception.TicketCancelledException;
import com.compass.ms_ticket_manager.model.Ticket;
import org.springframework.stereotype.Component;

@Component
public class TicketValidator {

    public void validateTicketNotCancelled(Ticket ticket) {
        if ("cancelado".equals(ticket.getStatus())) {
            throw new TicketCancelledException("Não é possível realizar a operação: o ticket está cancelado.");
        }
    }
}
