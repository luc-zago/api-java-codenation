package com.codenation.controllers;

import com.codenation.dtos.EventDTO;
import com.codenation.models.Event;
import com.codenation.services.EventServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    private EventDTO toEventDTO(Event event) {
        return modelMapper.map(event, EventDTO.class);
    }

    @PostMapping
    @ApiOperation(value = "Cria um novo evento")
    public ResponseEntity<Event> register(@RequestBody @Valid Event event) {
        Event eventCreated = eventService.save(event);
        if (eventCreated == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(eventCreated);
        }
    }

    @GetMapping("/all")
    @ApiOperation(value = "Retorna todos os eventos")
    public ResponseEntity<List<EventDTO>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(eventService.getAll()
                .stream().map(this::toEventDTO).collect(Collectors.toList()));
    }

    @GetMapping("/teste")
    @ApiOperation(value = "Retorna todos os eventos")
    public ResponseEntity<List<Event>> getAllTest(){
        return ResponseEntity.status(HttpStatus.OK).body(eventService.getAll();
    }
}
