package com.codenation.repositories;

import com.codenation.models.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Override
    Page<Event> findAll(Pageable pageable);

    List<Event> findAllByDescriptionAndLogAndOriginAndDateAndQuantityAndLevelDescription(
            String desc, String log, String origin, LocalDate date, Integer qtt, String  level);

    List<Event> findAllByDescriptionContainsAndOriginContainsAndUserEmailContainsAndLevelDescriptionContains(
            String description, String origin, String email, String levelDescription);

    List<Event> findAllByDescriptionContainsAndOriginContainsAndDateAndUserEmailContainsAndLevelDescriptionContains(
            String description, String origin, LocalDate date, String email, String levelDescription);

    List<Event> findAllByDescriptionContainsAndOriginContainsAndQuantityAndUserEmailContainsAndLevelDescriptionContains(
            String description, String origin, Integer quantity, String email, String levelDescription);

    List<Event> findAllByDescriptionContainsAndOriginContainsAndDateAndQuantityAndUserEmailContainsAndLevelDescriptionContains(
            String description, String origin, LocalDate date, Integer quantity, String email, String levelDescription);

}
