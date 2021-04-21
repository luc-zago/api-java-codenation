package com.codenation.services;

import com.codenation.models.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EventService extends ServiceInterface<Event> {

    public Event findById(Long id);
    public List<Event> findAllByDescription(String description, Pageable pageable);
    public List<Event> findAllByLog(String log, Pageable pageable);
    public List<Event> findAllByOrigin(String origin, Pageable pageable);
    public List<Event> findAllByDate(String day, String month, String year, Pageable pageable);
    public List<Event> findAllByQuantity(Integer quantity, Pageable pageable);
    public List<Event> findAllByEmail(String email, Pageable pageable);
    public List<Event> findAllByLevel(String level, Pageable pageable);
    public List<Event> getAll(Pageable pageable);

}
