package com.codenation.controllers;

import com.codenation.dtos.CreateEventDTO;
import com.codenation.dtos.EventDTO;
import com.codenation.dtos.EventDTOWithLog;
import com.codenation.models.Event;
import com.codenation.services.EventServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/event")
@AllArgsConstructor
@Api(value = "Api Rest Error Manager")
@CrossOrigin(origins = "*")
public class EventController {

    final private EventServiceImpl eventService;

    private ModelMapper modelMapper;

    private CreateEventDTO toCreateEventDTO(Event event) {
        return modelMapper.map(event, CreateEventDTO.class);
    }
    private EventDTO toEventDTO(Event event) {
        return new EventDTO(event.getId(), event.getDescription(), event.getOrigin(),
                event.getDate(), event.getQuantity(), event.getUser().getEmail(),
                event.getLevel().getDescription());
    }
    private EventDTOWithLog toEventDTOWithLog(Event event) {
        return new EventDTOWithLog(event.getId(), event.getDescription(), event.getLog(),
                event.getOrigin(), event.getDate(), event.getQuantity(), event.getUser().getEmail(),
                event.getLevel().getDescription());
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Busca evento por id")
    public ResponseEntity<EventDTOWithLog> findById(@PathVariable("id") Long id){
        Event event = eventService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(toEventDTOWithLog(event));
    }

    @PostMapping
    @ApiOperation(value = "Cria um novo evento")
    public ResponseEntity<CreateEventDTO> register(@RequestBody @Valid Event event) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Event eventCreated = eventService.register(event, principal);
        if (eventCreated == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(toCreateEventDTO(eventCreated));
        }
    }

    @GetMapping
    @ApiOperation(value = "Retorna todos os eventos")
    public ResponseEntity<List<EventDTO>> getAll(
            @RequestParam(value = "description", required = false, defaultValue = "") String description,
            @RequestParam(value = "origin", required = false, defaultValue = "") String origin,
            @RequestParam(value = "date", required = false) String date,
            @RequestParam(value = "quantity", required = false) Integer quantity,
            @RequestParam(value = "email", required = false, defaultValue = "") String email,
            @RequestParam(value = "level", required = false, defaultValue = "") String level,
            @RequestParam(value = "order", required = false, defaultValue = "") String order,
            @RequestParam(value = "sort", required = false, defaultValue = "") String sort,
            Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(eventService.filter(description,
                origin, date, quantity, email, level, pageable)
                .stream().map(this::toEventDTO).collect(Collectors.toList()));
    }

}
