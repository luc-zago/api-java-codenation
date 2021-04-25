package com.codenation.services;

import com.codenation.models.Event;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface EventService {

    Event findById(Long id);

    List<Event> filterAndSort(String description, String origin, LocalDate date, Integer quantity,
                              String email, String level, String order, String sort,
                              Pageable pageable);

}
