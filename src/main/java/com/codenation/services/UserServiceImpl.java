package com.codenation.services;

import com.codenation.models.User;
import com.codenation.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    final private UserRepository userRepository;

    @Override
    public User save(User object) {
        return this.userRepository.save(object);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }
}
