package com.codenation.repositories;

import com.codenation.models.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {

    @Override
    Page<Event> findAll(Pageable pageable);

    List<Event> findAllByDescriptionAndLogAndOriginAndDateAndQuantityAndLevelDescription(
            String desc, String log, String origin, LocalDate date, Integer qtt, String level);

    Page<Event> findAllByDescriptionContainsAndOriginContainsAndUserEmailContainsAndLevelDescriptionContains(
            String description, String origin, String email, String levelDescription, Pageable pageable);

    Page<Event> findAllByDescriptionContainsAndOriginContainsAndDateAndUserEmailContainsAndLevelDescriptionContains(
            String description, String origin, LocalDate date, String email, String levelDescription,
            Pageable pageable);

    Page<Event> findAllByDescriptionContainsAndOriginContainsAndQuantityAndUserEmailContainsAndLevelDescriptionContains(
            String description, String origin, Integer quantity, String email, String levelDescription,
            Pageable pageable);

    Page<Event> findAllByDescriptionContainsAndOriginContainsAndDateAndQuantityAndUserEmailContainsAndLevelDescriptionContains(
            String description, String origin, LocalDate date, Integer quantity, String email,
            String levelDescription, Pageable pageable);

    @Query("SELECT e FROM Event AS e WHERE " +
            "e.description LIKE CONCAT('%', :description, '%')  " +
            "AND e.origin LIKE CONCAT('%', :origin, '%')  " +
            "AND :date IS NULL OR e.date = :date " +
            "AND :quantity IS NULL OR e.quantity = :quantity " +
            "AND e.user.email LIKE CONCAT('%', :email, '%')  " +
            "AND e.level.description LIKE CONCAT('%', :level, '%')  " +
            "ORDER BY :order")
    Page<Event> filterAndSort(@Param("description") String description,
                              @Param("origin") String origin,
                              @Param("date") LocalDate date,
                              @Param("quantity") Integer quantity,
                              @Param("email") String email,
                              @Param("level") String level,
                              @Param("order") String orderParameter,
                              Pageable pageable);
}