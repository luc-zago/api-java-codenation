package com.codenation.services;

import com.codenation.models.Event;
import com.codenation.models.Level;
import com.codenation.models.User;
import com.codenation.repositories.EventRepository;
import com.codenation.repositories.LevelRepository;
import com.codenation.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {

    final private EventRepository eventRepository;
    final private UserRepository userRepository;
    final private LevelRepository levelRepository;

    @Override
    public Event save(Event object) {
        String desc = object.getDescription();
        String log = object.getLog();
        String origin = object.getOrigin();
        LocalDate date = object.getDate();
        Integer qtt = object.getQuantity();
        String email = object.getUser().getEmail();
        Long levelId = object.getLevel().getId();
        User user = this.userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado"));
        Level level = this.levelRepository.findById(levelId)
                .orElseThrow(() -> new NoSuchElementException("Level não encontrado"));
        String levelDesc = level.getDescription();
        List<Event> eventsList = this.eventRepository
                .findAllByDescriptionAndLogAndOriginAndDateAndQuantityAndLevelDescription(
                        desc, log, origin, date, qtt, levelDesc);
        if (!eventsList.isEmpty()) {
            throw new IllegalArgumentException("Evento já cadastrado");
        }
        object.setUser(user);
        object.setLevel(level);
        return this.eventRepository.save(object);
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
        List<Event> eventsList = this.eventRepository.findAllByDescription(description, pageable).getContent();
        return checkList(eventsList);
    }

    @Override
    public List<Event> findAllByLog(String log, Pageable pageable) {
        List<Event> eventsList = this.eventRepository.findAllByLog(log, pageable).getContent();
        return checkList(eventsList);
    }

    @Override
    public List<Event> findAllByOrigin(String origin, Pageable pageable) {
        List<Event> eventsList = this.eventRepository.findAllByOrigin(origin, pageable).getContent();
        return checkList(eventsList);
    }

    @Override
    public List<Event> findAllByDate(String date, Pageable pageable) {
        List<Event> eventsList = this.eventRepository.findAllByDate(date, pageable).getContent();
        return checkList(eventsList);
    }

    @Override
    public List<Event> findAllByQuantity(Integer quantity, Pageable pageable) {
        List<Event> eventsList = this.eventRepository.findAllByQuantity(quantity, pageable).getContent();
        return checkList(eventsList);
    }

    @Override
    public List<Event> findAllByEmail(String email, Pageable pageable) {
        List<Event> eventsList = this.eventRepository.findAllByUserEmail(email, pageable).getContent();
        return checkList(eventsList);
    }

    @Override
    public List<Event> findAllByLevel(String level, Pageable pageable) {
        List<Event> eventsList = this.eventRepository.findAllByLevelDescription(level, pageable)
                .getContent();
        return checkList(eventsList);
    }

    @Override
    public List<Event> getAll(Pageable pageable) {
        return this.eventRepository.findAll(pageable).getContent();
    }
}
