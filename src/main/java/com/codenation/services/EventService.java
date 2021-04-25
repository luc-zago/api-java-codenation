package com.codenation.services;

import com.codenation.models.Event;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface EventService {

    Event findById(Long id);

    List<Event> filterAndSort(String description, String origin, Date date, Integer quantity,
                              String email, String level, String order, String sort, Integer page,
                              Integer size, Pageable pageable);

}
