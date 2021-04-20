package com.codenation.controllers;

import com.codenation.dtos.LoginDTO;
import com.codenation.models.User;
import com.codenation.repositories.UserRepository;

import com.codenation.services.UserServiceImpl;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.InstanceAlreadyExistsException;
import javax.validation.Valid;

@RestController
@RequestMapping("/login")
@AllArgsConstructor
public class LoginController {

    //private final UserRepository userRepository;

    private final UserServiceImpl userService;

    private final ModelMapper modelMapper;

    private LoginDTO toLoginDTO(User user) {
        return modelMapper.map(user, LoginDTO.class);
    }

    @PostMapping
    @ApiOperation(value = "Retorna o usuário logado")
    public ResponseEntity<LoginDTO> loggedUser(@RequestBody User user) {
        System.out.println(user.getEmail());
        User loggedUser = userService.loggedUser(user.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(toLoginDTO(loggedUser));
    }
/*
    @PostMapping
    public ResponseEntity<UserDTO> login() {
        String email;

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            email = ((UserDetails)principal).getUsername();
        } else {
            email = principal.toString();
        }

        Optional<User> loggedUser = userRepository.findByEmail(email);
        return loggedUser.map(
                user -> ResponseEntity.status(HttpStatus.OK).body(modelMapper.map(user, UserDTO.class))
            ).orElseThrow(() -> new NoSuchElementException("Usuário não encontrado"));
        
        // () -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
    } */
}
