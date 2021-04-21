package com.codenation.services;

import com.codenation.models.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EventService extends ServiceInterface<Event> {

    public Event findById(Long id);
    public List<Event> checkFields(String description, String origin, String date,
                                   Integer quantity, String email, String level, Pageable pageable);
    public List<Event> getAll(Pageable pageable);

}
