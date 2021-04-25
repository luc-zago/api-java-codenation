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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/event")
@AllArgsConstructor
@Api(value = "Api Rest Error Manager")
public class EventController {

    final private EventServiceImpl eventService;

    private final ModelMapper modelMapper;

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
        Event eventCreated = eventService.register(event);
        if (eventCreated == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(toCreateEventDTO(eventCreated));
        }
    }

    @GetMapping
    @ApiOperation(value = "Retorna todos os eventos")
    public ResponseEntity<List<EventDTO>> getAll(
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "20") Integer size,
            @RequestParam(value = "description", required = false, defaultValue = "") String description,
            @RequestParam(value = "origin", required = false, defaultValue = "") String origin,
            @RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
            @RequestParam(value = "quantity", required = false) Integer quantity,
            @RequestParam(value = "user", required = false, defaultValue = "") String email,
            @RequestParam(value = "level", required = false, defaultValue = "") String level,
            @RequestParam(value = "order", required = false, defaultValue = "id") String order,
            @RequestParam(value = "sort", required = false, defaultValue = "asc") String sort,
            Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(eventService.filterAndSort(description,
                origin, date, quantity, email, level, order, sort, page, size, pageable)
                .stream().map(this::toEventDTO).collect(Collectors.toList()));
    }

}
