package com.compass.ms_ticket_manager.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.compass.ms_ticket_manager.model.Ticket;

public interface TicketRepository extends MongoRepository<Ticket, String> {
    List<Ticket> findByEventId(String eventId);
}