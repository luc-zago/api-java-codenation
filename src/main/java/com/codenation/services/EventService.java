package com.codenation.services;

import com.codenation.models.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EventService extends ServiceInterface<Event> {

    public Event findById(Long id);
    public List<Event> filter(String description, String origin, String date, Integer quantity,
                              String email, String level, Integer page, Integer size,
                              String order, String sort,
                              Pageable pageable);
    public List<Event> getAll(Pageable pageable);

}
