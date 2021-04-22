package com.codenation.services;

import com.codenation.models.Event;
import com.codenation.models.Level;
import com.codenation.models.User;
import com.codenation.repositories.EventRepository;
import com.codenation.repositories.LevelRepository;
import com.codenation.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {

    final private EventRepository eventRepository;
    final private UserRepository userRepository;
    final private LevelRepository levelRepository;

    private String getLoggedUserEmail() {
        String email;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            email = ((UserDetails)principal).getUsername();
        } else {
            email = principal.toString();
        }
        return email;
    }

    @Override
    public Event save(Event event) {
        return this.eventRepository.save(event);
    }

    public Event register(Event event) {
        Long levelId = event.getLevel().getId();
        User user = this.userRepository.findByEmail(getLoggedUserEmail())
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

    private List<Event> sortEvents(List<Event> events, String order, String sort) {
        switch (order) {
            case "description": {
                if (sort.equals("ASC")) {
                    return events.stream().sorted(Comparator.comparing(Event::getDescription))
                            .collect(Collectors.toList());
                } else {
                    return events.stream().sorted(Comparator.comparing(Event::getDescription).reversed())
                            .collect(Collectors.toList());
                }
            }
            case "origin": {
                if (sort.equals("ASC")) {
                    return events.stream().sorted(Comparator.comparing(Event::getOrigin))
                            .collect(Collectors.toList());
                } else {
                    return events.stream().sorted(Comparator.comparing(Event::getOrigin).reversed())
                            .collect(Collectors.toList());
                }
            }
            case "date": {
                if (sort.equals("ASC")) {
                    return events.stream().sorted(Comparator.comparing(Event::getDate))
                            .collect(Collectors.toList());
                } else {
                    return events.stream().sorted(Comparator.comparing(Event::getDate).reversed())
                            .collect(Collectors.toList());
                }
            }
            case "quantity": {
                if (sort.equals("ASC")) {
                    return events.stream().sorted(Comparator.comparingInt(Event::getQuantity))
                            .collect(Collectors.toList());
                } else {
                    return events.stream().sorted(Comparator.comparingInt(Event::getQuantity).reversed())
                            .collect(Collectors.toList());
                }
            }
            /*case "user": {
                if (sort.equals("ASC")) {
                    return events.stream().sorted(Comparator.comparing(Event::getUser))
                            .collect(Collectors.toList());
                } else {
                    return events.stream().sorted(Comparator.comparing(Event::getUser).reversed())
                            .collect(Collectors.toList());
                }
            }
            case "level": {
                if (sort.equals("ASC")) {
                    return events.stream().sorted(Comparator.comparing(Event::getLevel))
                            .collect(Collectors.toList());
                } else {
                    return events.stream().sorted(Comparator.comparing(Event::getLevel).reversed())
                            .collect(Collectors.toList());
                }
            } */
            default: {
                return events;
            }
        }
    }

    @Override
    public List<Event> filterAndSort(String description, String origin, LocalDate date, Integer quantity,
                                     String email, String level, Integer page, Integer size,
                                     String order, String sort,
                                     Pageable pageable) {
        List<Event> eventList = new ArrayList<>();
        if (date == null && quantity == null) {
            eventList.addAll(eventRepository.findAllByDescriptionContainsAndOriginContainsAndUserEmailContainsAndLevelDescriptionContains(
                            description, origin, email, level, pageable).getContent());
        } else if (date == null) {
            eventList.addAll(eventRepository.findAllByDescriptionContainsAndOriginContainsAndQuantityAndUserEmailContainsAndLevelDescriptionContains(
                    description, origin, quantity, email, level, pageable).getContent());
        } else if (quantity == null) {
            eventList.addAll(eventRepository.findAllByDescriptionContainsAndOriginContainsAndDateAndUserEmailContainsAndLevelDescriptionContains(
                    description, origin, date, email, level, pageable).getContent());
        } else {
            eventList.addAll(eventRepository.findAllByDescriptionContainsAndOriginContainsAndDateAndQuantityAndUserEmailContainsAndLevelDescriptionContains(
                    description, origin, date, quantity, email, level, pageable).getContent());
        }

        return sortEvents(eventList, order, sort);
    }
}
