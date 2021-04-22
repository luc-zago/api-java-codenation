package com.codenation.repositories;

import com.codenation.models.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Override
    Page<Event> findAll(Pageable pageable);
    List<Event> findAllByDescriptionContainsAndOriginContainsAndUserEmailContainsAndLevelDescriptionContains(
            String description, String origin, String email, String levelDescription);
    List<Event> findAllByDescriptionAndLogAndOriginAndDateAndQuantityAndLevelDescription(
            String desc, String log, String origin, LocalDate date, Integer qtt, String  level);
}
