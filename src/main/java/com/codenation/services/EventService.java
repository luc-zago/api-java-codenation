package com.codenation.services;

import com.codenation.models.Event;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface EventService extends ServiceInterface<Event> {

    public Event findById(Long id);
    public List<Event> findAllByDescription(String description, Pageable pageable);
    public List<Event> findAllByLog(String log, Pageable pageable);
    public List<Event> findAllByOrigin(String origin, Pageable pageable);
    public List<Event> findAllByDate(String date, Pageable pageable);
    public List<Event> findAllByQuantity(Integer quantity, Pageable pageable);
    public List<Event> getAll(Pageable pageable);

}
