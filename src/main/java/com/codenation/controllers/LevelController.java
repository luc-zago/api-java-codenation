package com.codenation.controllers;

import com.codenation.dtos.EventDTOWithLog;
import com.codenation.models.Event;
import com.codenation.models.Level;
import com.codenation.models.User;
import com.codenation.services.LevelServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.InstanceAlreadyExistsException;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/level")
@AllArgsConstructor
@Api(value = "Api Rest Error Manager")
@CrossOrigin(origins = "*")
public class LevelController {

    final private LevelServiceImpl levelService;

    @GetMapping
    @ApiOperation(value = "Retorna todos os tipos de level")
    public ResponseEntity<List<Level>> levels() {
        return ResponseEntity.status(HttpStatus.OK).body(this.levelService.getAll());
    }

    @PostMapping
    @ApiOperation(value = "Cria um novo tipo de level")
    public ResponseEntity<Level> register(@RequestBody @Valid Level level) throws InstanceAlreadyExistsException {
        Level levelCreated = levelService.register(level);
        if (levelCreated == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(levelCreated);
        }
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Atualiza um level por id")
    public ResponseEntity<Level> updateById(
            @PathVariable("id") Long id,
            @RequestBody Level level) {
        Level updatedLevel = levelService.update(level, id);
        return ResponseEntity.status(HttpStatus.OK).body(updatedLevel);
    }
}
