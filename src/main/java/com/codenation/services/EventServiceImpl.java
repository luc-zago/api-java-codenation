package com.codenation.services;

import com.codenation.models.Event;
import com.codenation.models.Level;
import com.codenation.models.User;
import com.codenation.repositories.EventRepository;
import com.codenation.repositories.LevelRepository;
import com.codenation.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {

    final private EventRepository eventRepository;
    final private UserRepository userRepository;
    final private LevelRepository levelRepository;

    @Override
    public Event save(Event object) {
        Long userId = object.getUser().getId();
        System.out.println(userId);
        Long levelId = object.getLevel().getId();
        System.out.println(levelId);
        Optional<User> user = this.userRepository.findById(userId);
        System.out.println(user.get().getEmail());
        Optional<Level> level = this.levelRepository.findById(levelId);
        System.out.println(level.get().getDescription());
        if (!user.isPresent()){
            throw new NullPointerException("Usuário não encontrado");
        }
        if (!level.isPresent()){
            throw new NullPointerException("Level não encontrado");
        }
        object.setUser(user.get());
        object.setLevel(level.get());
        return this.eventRepository.save(object);
    }

    @Override
    public List<Event> getAll() {
        return eventRepository.findAll();
    }
}
