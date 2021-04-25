package com.codenation.services;

import com.codenation.enums.Authority;
import com.codenation.models.User;
import com.codenation.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.InstanceAlreadyExistsException;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public User register(User user) throws InstanceAlreadyExistsException {
        Optional<User> checkUser = this.userRepository.findByEmail(user.getEmail());

        if (checkUser.isPresent()) {
            throw new InstanceAlreadyExistsException("Usuário já cadastrado");
        } else {
            user.setPassword(this.passwordEncoder().encode(user.getPassword()));
            user.setAuthority(Authority.USER);
            return userRepository.save(user);
        }
    }

    @Override
    public List<User> getAll() {
        return this.userRepository.findAll();
    }
}
