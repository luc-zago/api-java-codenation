package com.codenation.services;

import com.codenation.enums.SearchOperation;
import com.codenation.models.Event;
import com.codenation.models.Level;
import com.codenation.models.User;
import com.codenation.utils.EventSpecification;
import com.codenation.repositories.EventRepository;
import com.codenation.repositories.LevelRepository;
import com.codenation.repositories.UserRepository;
import com.codenation.utils.SearchCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.management.InstanceAlreadyExistsException;
import java.time.LocalDate;
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

    public Event register(Event event) throws InstanceAlreadyExistsException {
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
            throw new InstanceAlreadyExistsException("Evento já cadastrado");
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
    public Event update(Event event, Long id) {
        Event oldEvent = eventRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Evento não encontrado"));
        Level level = levelRepository.findById(event.getLevel().getId())
                .orElseThrow(() -> new NoSuchElementException("Level não encontrado"));
        String email = getLoggedUserEmail();
        if (!oldEvent.getUser().getEmail().equals(email) && !email.equals("admin@admin.com")) {
            throw new IllegalArgumentException("Usuário não autorizado");
        }
        event.setId(id);
        event.setUser(oldEvent.getUser());
        event.setLevel(level);
        return eventRepository.save(event);
    }

    @Override
    public void deleteById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Evento não encontrado"));
        String email = getLoggedUserEmail();
        if (!event.getUser().getEmail().equals(email) && !email.equals("admin@admin.com")) {
            throw new IllegalArgumentException("Usuário não autorizado");
        }
        eventRepository.delete(event);
    }

    @Override
    public List<Event> filterAndSort(String description, String origin, LocalDate date, Integer quantity,
                                     String email, String level, String order, String sort, Integer page,
                                     Integer size, Pageable pageable) {
        EventSpecification filter = new EventSpecification();
        filter.add(new SearchCriteria("description", null, description, SearchOperation.LIKE));
        filter.add(new SearchCriteria("origin", null, origin, SearchOperation.LIKE));
        filter.add(new SearchCriteria("user", "email", email, SearchOperation.LIKE));
        filter.add(new SearchCriteria("level", "description", level, SearchOperation.LIKE));
        if (date != null) {
            filter.add(new SearchCriteria("date", null, date, SearchOperation.EQUAL));
        }
        if (quantity != null) {
            filter.add(new SearchCriteria("quantity", null, quantity, SearchOperation.EQUAL));
        }
        if (order.equals("user")) {
            order = "user.email";
        } else if (order.equals("level")) {
            order = "level.description";
        }
        pageable = PageRequest.of(page, size, Sort.by(order).ascending());
        if (sort.equals("DESC")) {
            pageable = PageRequest.of(page, size, Sort.by(order).descending());
        }
        return this.eventRepository.findAll(filter, pageable).getContent();
    }
}
