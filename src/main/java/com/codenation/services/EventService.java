package com.codenation.services;

import com.codenation.models.Event;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface EventService {

    public Event findById(Long id);
    public Event update(Event event, Long id);
    public void deleteById(Long id);

    public List<Event> filterAndSort(String description, String origin, LocalDate date, Integer quantity,
                              String email, String level, String order, String sort,
                                     Integer page, Integer size, Pageable pageable);

    public List<Event> getAll(Pageable pageable);

}
