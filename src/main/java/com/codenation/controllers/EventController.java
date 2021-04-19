package com.codenation.controllers;

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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/event")
@AllArgsConstructor
@Api(value = "Api Rest Error Manager")
@CrossOrigin(origins = "*")
public class EventController {

    final private EventServiceImpl eventService;

    private ModelMapper modelMapper;

    private EventDTO toEventDTO(Event event) {
        return modelMapper.map(event, EventDTO.class);
    }
    private EventDTOWithLog toEventDTOWithLog(Event event) {
        return modelMapper.map(event, EventDTOWithLog.class);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Busca evento por id")
    public ResponseEntity<EventDTOWithLog> findById(@PathVariable("id") Long id){
        Event event = eventService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(toEventDTOWithLog(event));
    }

    @GetMapping("/{description}")
    @ApiOperation(value = "Busca evento por descrição")
    public ResponseEntity<List<EventDTO>> findByDescription(@PathVariable("description") String desc, Pageable pageable){
        List<Event> eventsList = eventService.findByDescription(desc, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(eventsList
                .stream().map(this::toEventDTO).collect(Collectors.toList()));
    }

    @PostMapping
    @ApiOperation(value = "Cria um novo evento")
    public ResponseEntity<EventDTO> register(@RequestBody @Valid Event event) {
        Event eventCreated = eventService.save(event);
        if (eventCreated == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(toEventDTO(eventCreated));
        }
    }

    @GetMapping("/all")
    @ApiOperation(value = "Retorna todos os eventos")
    public ResponseEntity<List<EventDTO>> getAll(Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(eventService.getAll(pageable)
                .stream().map(this::toEventDTO).collect(Collectors.toList()));
    }

}
