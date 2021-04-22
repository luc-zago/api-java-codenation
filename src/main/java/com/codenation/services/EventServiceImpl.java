package com.codenation.services;

import com.codenation.models.Event;
import com.codenation.models.Level;
import com.codenation.models.User;
import com.codenation.repositories.EventRepository;
import com.codenation.repositories.LevelRepository;
import com.codenation.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
    public List<Event> filter(String description, String origin, String date, Integer quantity,
                              String email, String level, Integer page, Integer size,
                              String order, String sort,
                              Pageable pageable) {
        pageable = PageRequest.of(page, size, Sort.by(order).ascending());
        sort = sort.toLowerCase();
        if (sort == "desc") {
            pageable = PageRequest.of(page, size, Sort.by(order).descending());
        }
        List<Event> eventList = this.eventRepository.findAllByDescriptionContainsAndOriginContainsAndUserEmailContainsAndLevelDescriptionContains(
                description, origin, email, level);
        if (date != null && date.length() == 10) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(date, formatter);
            Stream<Event> eventStream = eventList.stream().filter(event -> event.getDate().isEqual(localDate));
            eventList = eventStream.collect(Collectors.toList());
        }
        if (quantity != null) {
            Stream<Event> eventStream = eventList.stream().filter(event -> event.getQuantity() == quantity);
            eventList = eventStream.collect(Collectors.toList());
        }
        System.out.println(eventList.size());
        return new PageImpl<Event>(eventList, pageable, eventList.size()).getContent();
    }
}
