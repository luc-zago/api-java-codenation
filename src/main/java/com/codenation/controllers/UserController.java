package com.codenation.controllers;

import com.codenation.dtos.UserDTO;
import com.codenation.services.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
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
/*
    @GetMapping("/teste")
    @ApiOperation(value = "faz um teste")
    public String teste() {
        return "Olá teste";
    } */

    @PostMapping
    @ApiOperation(value = "Cria um novo usuário")
    public ResponseEntity<User> register(@RequestBody @Valid User user) throws InstanceAlreadyExistsException {
        User userCreated = userService.register(user);
        if (userCreated == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
        }
    }
    
    @GetMapping("/all")
    @ApiOperation(value = "Retorna todos os usuários")
    public ResponseEntity<List<UserDTO>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAll()
            .stream().map(this::toUserDTO).collect(Collectors.toList()));
    }
}
