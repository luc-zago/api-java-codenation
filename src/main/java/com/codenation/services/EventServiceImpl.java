package com.codenation.services;

import com.codenation.models.Event;
import com.codenation.models.Level;
import com.codenation.models.User;
import com.codenation.repositories.EventRepository;
import com.codenation.repositories.LevelRepository;
import com.codenation.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {

    final private EventRepository eventRepository;
    final private UserRepository userRepository;
    final private LevelRepository levelRepository;

    @Override
    public Event save(Event event) {
        return this.eventRepository.save(event);
    }

    public Event register(Event event, Object principal) {
        String email;

        if (principal instanceof UserDetails) {
            email = ((UserDetails)principal).getUsername();
        } else {
            email = principal.toString();
        }
        Long levelId = event.getLevel().getId();
        User user = this.userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado"));
        Level level = this.levelRepository.findById(levelId)
                .orElseThrow(() -> new NoSuchElementException("Level não encontrado"));
        List<Event> eventsList = this.eventRepository
                .findAllByDescriptionAndLogAndOriginAndDateAndQuantityAndLevelDescription(
                        event.getDescription(), event.getLog(), event.getOrigin(), event.getDate(),
                        event.getQuantity(), level.getDescription());
        if (!eventsList.isEmpty()) {
            throw new IllegalArgumentException("Evento já cadastrado");
        }
        event.setUser(user);
        event.setLevel(level);
        return save(event);
    }

    @Override
    public Event findById(Long id) {
        return this.eventRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Evento não encontrado"));
    }

    @Override
    public List<Event> getAll(Pageable pageable) {
        return this.eventRepository.findAll(pageable).getContent();
    }

    @Override
    public List<Event> checkFields(String description, String origin, String date,
                                   Integer quantity, String email, String level, Pageable pageable) {
        if (description == null) {
            description = "";
        }
        if (origin == null) {
            origin = "";
        }
        if (email == null) {
            email = "";
        }
        if (level == null) {
            level = "";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);
        if (localDate == null && quantity == null) {
            return this.eventRepository
                    .findAllByDescriptionContainsAndOriginContainsAndUserEmailContainsAndLevelDescriptionContains(
                            description, origin, email, level, pageable
                    ).getContent();
        }
        if (localDate != null && quantity == null) {
            return this.eventRepository
                    .findAllByDescriptionContainsAndOriginContainsAndUserEmailContainsAndLevelDescriptionContainsAndDate(
                            description, origin, email, level, localDate, pageable
            ).getContent();
        }
        if (localDate == null && quantity != null) {
            return this.eventRepository
                    .findAllByDescriptionContainsAndOriginContainsAndUserEmailContainsAndLevelDescriptionContainsAndQuantity(
                            description, origin, email, level, quantity, pageable
                    ).getContent();
        }
        return this.eventRepository
                .findAllByDescriptionContainsAndOriginContainsAndUserEmailContainsAndLevelDescriptionContainsAndDateAndQuantity(
                        description, origin, email, level, localDate, quantity, pageable
                ).getContent();
    }

}
