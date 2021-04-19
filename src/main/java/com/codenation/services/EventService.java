package com.codenation.services;

import com.codenation.models.Event;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface EventService extends ServiceInterface<Event> {

    public Event findById(Long id);

    public List<Event> getAll(Pageable pageable);

}
