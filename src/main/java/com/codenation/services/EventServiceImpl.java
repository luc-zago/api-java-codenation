package com.codenation.services;

import com.codenation.models.Event;
import com.codenation.models.Level;
import com.codenation.models.User;
import com.codenation.repositories.EventRepository;
import com.codenation.repositories.LevelRepository;
import com.codenation.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final LevelRepository levelRepository;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository, UserRepository userRepository,
                            LevelRepository levelRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.levelRepository = levelRepository;
    }

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

    public Event register(Event event) {
        Long levelId = event.getLevel().getId();
        User user = userRepository.findByEmail(getLoggedUserEmail())
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado"));
        Level level = levelRepository.findById(levelId)
                .orElseThrow(() -> new NoSuchElementException("Level não encontrado"));
        List<Event> eventsList = eventRepository
                .findAllByDescriptionAndLogAndOriginAndDateAndQuantityAndLevelDescription(
                        event.getDescription(), event.getLog(), event.getOrigin(), event.getDate(),
                        event.getQuantity(), level.getDescription());
        if (!eventsList.isEmpty()) {
            throw new IllegalArgumentException("Evento já cadastrado");
        }
        event.setUser(user);
        event.setLevel(level);
        return eventRepository.save(event);
    }

    @Override
    public Event findById(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Evento não encontrado"));
    }

    @Override
    public List<Event> filterAndSort(String description, String origin, Date date, Integer quantity,
                                     String email, String level, String order, String sort, Integer page,
                                     Integer size, Pageable pageable) {
        pageable = PageRequest.of(page, size, Sort.by(order).ascending());
        sort = sort.toUpperCase();
        /*if (sort.equals("DESC")) {
            List<Event> teste = eventRepository.filterAndSort(description, origin, date, quantity, email,
                    level, order, sort, pageable).getContent();
            return teste;
        } */
        List<Event> teste = eventRepository.filterAndSort(description, origin, date, quantity, email, level, pageable).getContent();
        return teste;
    }

}
