package com.codenation.repositories;

import com.codenation.models.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Override
    Page<Event> findAll(Pageable pageable);
    Page<Event> findAllByDescription(String description, Pageable pageable);
    Page<Event> findAllByLog(String log, Pageable pageable);
    Page<Event> findAllByOrigin(String origin, Pageable pageable);
    Page<Event> findAllByDate(String date, Pageable pageable);
    Page<Event> findAllByQuantity(Integer quantity, Pageable pageable);
}
