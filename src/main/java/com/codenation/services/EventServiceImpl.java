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
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
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
        sort = sort.toUpperCase();
        if (sort.equals("DESC")) {
            pageable = PageRequest.of(page, size, Sort.by(order).descending());
        }
        return this.eventRepository.findAll(filter, pageable).getContent();
    }
}
