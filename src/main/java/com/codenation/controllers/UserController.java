package com.codenation.controllers;

import com.codenation.services.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.codenation.models.User;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
@Api(value = "Api Rest Error Manager")
@CrossOrigin(origins = "*")
public class UserController {

    final private UserServiceImpl userService;

    @GetMapping("/teste")
    @ApiOperation(value = "faz um teste")
    public String teste() {
        return "Olá teste";
    }

    @PostMapping
    @ApiOperation(value = "Cria um novo usuário")
    public ResponseEntity<User> register(User user) {
        User userCreated = userService.save(user);
        if (userCreated == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
        }
    }
    
    @PostMapping("/all")
    public ResponseEntity<List<User>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(userService.getAll());
    }
}
