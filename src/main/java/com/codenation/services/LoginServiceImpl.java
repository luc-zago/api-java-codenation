package com.codenation.services;

import com.codenation.models.User;
import com.codenation.repositories.UserRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class LoginServiceImpl {
    private final UserRepository userRepository;

    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }
}
