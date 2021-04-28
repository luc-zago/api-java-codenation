package com.codenation.controllers;

import com.codenation.dtos.EventDTOWithLog;
import com.codenation.dtos.UserDTO;
import com.codenation.dtos.UserDTOWithId;
import com.codenation.models.Event;
import com.codenation.models.Level;
import com.codenation.services.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.Errors;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import com.codenation.models.User;
import org.springframework.http.HttpStatus;
import org.modelmapper.ModelMapper;

import javax.management.InstanceAlreadyExistsException;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@Api(value = "Api Rest Error Manager")
@CrossOrigin(origins = "*")
public class UserController {

    final private UserServiceImpl userService;

    private ModelMapper modelMapper;

    private UserDTO toUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    private UserDTOWithId toUserWithId(User user) {
        return modelMapper.map(user, UserDTOWithId.class);
    }

    @PostMapping
    @ApiOperation(value = "Cria um novo usuário")
    public ResponseEntity<UserDTO> register(@RequestBody @Valid User user) throws InstanceAlreadyExistsException {
        User userCreated = userService.register(user);
        if (userCreated == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(toUserDTO(userCreated));
        }
    }
    
    @GetMapping
    @ApiOperation(value = "Retorna todos os usuários")
    public ResponseEntity<List<UserDTOWithId>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAll()
            .stream().map(this::toUserWithId).collect(Collectors.toList()));
    }

    @PutMapping
    @ApiOperation(value = "Atualiza um usuário")
    public ResponseEntity<UserDTO> update(@RequestBody @Valid User user) {
        User updatedUser = userService.update(user);
        return ResponseEntity.status(HttpStatus.OK).body(toUserDTO(updatedUser));
    }
}
