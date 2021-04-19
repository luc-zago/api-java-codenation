package com.codenation.controllers;

import com.codenation.dtos.UserDTO;
import com.codenation.models.User;
import com.codenation.repositories.UserRepository;
import com.codenation.services.LoginServiceImpl;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/login")
@AllArgsConstructor
public class LoginController {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

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
    }
}
