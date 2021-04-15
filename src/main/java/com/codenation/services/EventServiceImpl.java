package com.codenation.services;

import com.codenation.models.Event;
import com.codenation.repositories.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {

    final private EventRepository eventRepository;

    @Override
    public Event save(Event object) {
        return this.eventRepository.save(object);
    }

    @Override
    public List<Event> getAll() {
        return eventRepository.findAll();
    }
}
