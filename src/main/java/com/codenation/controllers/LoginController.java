package com.codenation.controllers;

import com.codenation.dtos.LoginDTO;
import com.codenation.dtos.UserDTO;
import com.codenation.models.Level;
import com.codenation.models.User;
import com.codenation.repositories.UserRepository;

import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/login")
@AllArgsConstructor
public class LoginController {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private UserDTO toUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    private User getLoggedUser() {
        String email;

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            email = ((UserDetails)principal).getUsername();
        } else {
            email = principal.toString();
        }

        User loggedUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado"));
        return loggedUser;
    }

    @GetMapping
    @ApiOperation(value = "Retorna o email, nome e sobrenome do usuário logado")
    public ResponseEntity<UserDTO> login() {
        return ResponseEntity.status(HttpStatus.OK).body(toUserDTO(this.getLoggedUser()));
        // () -> ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
    }

    @PutMapping
    @ApiOperation(value = "Atualiza os dados do usuário logado")
    public ResponseEntity<UserDTO> updateLoggedUser() {
        User user = this.getLoggedUser();
        return ResponseEntity.status(HttpStatus.OK).body(toUserDTO(user));
    }
}
