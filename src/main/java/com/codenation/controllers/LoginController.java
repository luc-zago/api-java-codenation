package com.codenation.controllers;

import com.codenation.dtos.UserDTO;
import com.codenation.models.User;
import com.codenation.repositories.UserRepository;

import com.codenation.services.LoggedUser;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/login")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class LoginController {

    private final ModelMapper modelMapper;

    private final LoggedUser loggedUser;

    private UserDTO toUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    @GetMapping
    @ApiOperation(value = "Retorna o email, nome e sobrenome do usuário logado")
    public ResponseEntity<UserDTO> login() {
        return ResponseEntity.status(HttpStatus.OK).body(toUserDTO(loggedUser.get()));
    }
}
