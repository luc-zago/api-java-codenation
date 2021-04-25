package com.codenation.services;

import com.codenation.models.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface EventService extends ServiceInterface<Event> {

    public Event findById(Long id);
    public Event updateById(Long id);
    public void deleteById(Long id);

    public List<Event> filterAndSort(String description, String origin, LocalDate date, Integer quantity,
                              String email, String level, Integer page, Integer size,
                              String order, String sort,
                              Pageable pageable);

    public List<Event> getAll(Pageable pageable);

}
