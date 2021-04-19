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

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {

    final private EventRepository eventRepository;
    final private UserRepository userRepository;
    final private LevelRepository levelRepository;

    @Override
    public Event save(Event object) {
        String email = object.getUser().getEmail();
        Long levelId = object.getLevel().getId();
        User user = this.userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado"));
        Level level = this.levelRepository.findById(levelId)
                .orElseThrow(() -> new NoSuchElementException("Level não encontrado"));
        object.setUser(user);
        object.setLevel(level);
        return this.eventRepository.save(object);
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
}
