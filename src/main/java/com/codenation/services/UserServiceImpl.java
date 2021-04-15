package com.codenation.services;

import com.codenation.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService{

    final private UserRepository userRepository;

    @Override
    public Object save(Object object) {
        return null;
    }
}
