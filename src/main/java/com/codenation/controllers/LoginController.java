package com.codenation.controllers;

import com.codenation.models.User;
import com.codenation.services.LoginServiceImpl;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
@AllArgsConstructor
public class LoginController {
    private final LoginServiceImpl loginService;

    @PostMapping
    public User login(@RequestBody String login) {
        return loginService.findByLogin(login);
    }
}
