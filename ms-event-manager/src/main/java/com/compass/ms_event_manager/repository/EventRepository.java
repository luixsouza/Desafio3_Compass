package com.compass.ms_event_manager.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.compass.ms_event_manager.model.Event;

public interface EventRepository extends MongoRepository<Event, String> {
    List<Event> findAllByOrderByEventNameAsc();
}