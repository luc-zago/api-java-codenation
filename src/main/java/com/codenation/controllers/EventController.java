package com.codenation.controllers;

import com.codenation.dtos.CreateEventDTO;
import com.codenation.dtos.EventDTO;
import com.codenation.dtos.EventDTOWithLog;
import com.codenation.models.Event;
import com.codenation.services.EventServiceImpl;
import com.codenation.utils.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceAlreadyExistsException;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/event")
@AllArgsConstructor
@Api(value = "Api Rest Error Manager")
@CrossOrigin(origins = "*")
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
    @ApiOperation(value = "Retorna um evento por com base no 'id' passado pela url")
    public ResponseEntity<EventDTOWithLog> findById(@PathVariable("id") Long id){
        Event event = eventService.findById(id);
        return ResponseEntity.status(HttpStatus.OK).body(toEventDTOWithLog(event));
    }

    @PostMapping
    @ApiOperation(value = "Cria um novo evento")
    public ResponseEntity<CreateEventDTO> register(@RequestBody @Valid Event event) throws InstanceAlreadyExistsException {
        Event eventCreated = eventService.register(event);
        if (eventCreated == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(toCreateEventDTO(eventCreated));
        }
    }

    @GetMapping
    @ApiOperation(value = "Retorna todos os eventos ocultando o campo 'log'. O retorno pode ser filtrado " +
            "e ordenado de acordo com os seguintes par??metros que podem ser passados via url: a) description: " +
            "descri????o do evento; b) origin: origem do evento; c) date: data do evento; d) quantity: " +
            "quantidade de ocorr??ncias do evento; e) user: email do usu??rio que registrou o evento; " +
            "f) level: descri????o do level do evento; " +
            "Os eventos tamb??m podem ser ordenados por qualquer um dos atributos mencionados atrav??s " +
            "do par??metro 'order', que tamb??m ?? passado via url e recebe como valor o nome do atributo que " +
            "deve ser tomado como refer??ncia (Ex: description, user, level). Se nenhum par??metro ?? passado, " +
            "o retorno ?? ordenado pelo atributo 'id' em ordem ascendente. Para alterar o retorno para ordem " +
            "descendente, deve ser utilizado o par??metro 'sort' via url com o valor 'desc'.")
    public ResponseEntity<List<EventDTO>> getAll(
            @RequestParam(value = "description", required = false, defaultValue = "") String description,
            @RequestParam(value = "origin", required = false, defaultValue = "") String origin,
            @RequestParam(value = "date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate date,
            @RequestParam(value = "quantity", required = false) Integer quantity,
            @RequestParam(value = "user", required = false, defaultValue = "") String email,
            @RequestParam(value = "level", required = false, defaultValue = "") String level,
            @RequestParam(value = "order", required = false, defaultValue = "id") String order,
            @RequestParam(value = "sort", required = false, defaultValue = "asc") String sort,
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size,
            Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(eventService.filterAndSort(description,
                origin, date, quantity, email, level, order, sort, page, size, pageable)
                .stream().map(this::toEventDTO).collect(Collectors.toList()));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deleta um evento com base no 'id' passado pela url")
    public ResponseEntity<Response> deleteById(@PathVariable("id") Long id) {
        eventService.deleteById(id);
        Response response = new Response("Evento apagado com sucesso");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Atualiza um evento com base no 'id' passado pela url")
    public ResponseEntity<EventDTOWithLog> updateById(
            @PathVariable("id") Long id,
            @RequestBody @Valid Event event) {
        Event updatedEvent = eventService.update(event, id);
        return ResponseEntity.status(HttpStatus.OK).body(toEventDTOWithLog(updatedEvent));
    }

}
