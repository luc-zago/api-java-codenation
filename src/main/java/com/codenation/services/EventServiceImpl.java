package com.codenation.services;

import com.codenation.repositories.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService{

    final private EventRepository eventRepository;

    @Override
    public Object save(Object object) {
        return null;
    }
}
