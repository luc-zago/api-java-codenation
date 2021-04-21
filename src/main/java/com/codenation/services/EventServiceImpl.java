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

    public List<Event> checkList(List<Event> eventsList) {
        if (eventsList.isEmpty()) {
            throw new IllegalArgumentException("Nenhum evento encontrado");
        }
        return eventsList;
    }

    @Override
    public List<Event> findAllByDescription(String description, Pageable pageable) {
        if (description == null || description.isEmpty()) {
            return this.eventRepository.findAll(pageable).getContent();
        }
        List<Event> eventsList = this.eventRepository.findAllByDescription(description, pageable)
                .getContent();
        return checkList(eventsList);
    }

    @Override
    public List<Event> findAllByLog(String log, Pageable pageable) {
        if (log == null || log.isEmpty()) {
            return this.eventRepository.findAll(pageable).getContent();
        }
        List<Event> eventsList = this.eventRepository.findAllByLog(log, pageable).getContent();
        return checkList(eventsList);
    }

    @Override
    public List<Event> findAllByOrigin(String origin, Pageable pageable) {
        if (origin == null || origin.isEmpty()) {
            return this.eventRepository.findAll(pageable).getContent();
        }
        List<Event> eventsList = this.eventRepository.findAllByOrigin(origin, pageable).getContent();
        return checkList(eventsList);
    }

    @Override
    public List<Event> findAllByDate(String day, String month, String year, Pageable pageable) {
        String date = year + "-" + month + "-" + day;
        if (date == null || date.isEmpty()) {
            return this.eventRepository.findAll(pageable).getContent();
        }
        System.out.println(date);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);
        List<Event> eventsList = this.eventRepository.findAllByDate(localDate, pageable).getContent();
        return checkList(eventsList);
    }

    @Override
    public List<Event> findAllByQuantity(Integer quantity, Pageable pageable) {
        if (quantity == null) {
            return this.eventRepository.findAll(pageable).getContent();
        }
        List<Event> eventsList = this.eventRepository.findAllByQuantity(quantity, pageable).getContent();
        return checkList(eventsList);
    }

    @Override
    public List<Event> findAllByEmail(String email, Pageable pageable) {
        if (email == null || email.isEmpty()) {
            return this.eventRepository.findAll(pageable).getContent();
        }
        List<Event> eventsList = this.eventRepository.findAllByUserEmail(email, pageable).getContent();
        return checkList(eventsList);
    }

    @Override
    public List<Event> findAllByLevel(String level, Pageable pageable) {
        if (level == null || level.isEmpty()) {
            return this.eventRepository.findAll(pageable).getContent();
        }
        List<Event> eventsList = this.eventRepository.findAllByLevelDescription(level, pageable)
                .getContent();
        return checkList(eventsList);
    }

    @Override
    public List<Event> getAll(Pageable pageable) {
        return this.eventRepository.findAll(pageable).getContent();
    }

    public List<Event> checkFields(String description, String origin, String date,
                                   Integer quantity, String email, String level, Pageable pageable) {
        System.out.println(date);

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
        List<Event> eventList = this.eventRepository
                .findAllByDescriptionContainsAndOriginContainsAndUserEmailContainsAndLevelDescriptionContains(
                description, origin, email, level, pageable
        ).getContent();
        System.out.println(eventList.size());
        return eventList;
    }

}
