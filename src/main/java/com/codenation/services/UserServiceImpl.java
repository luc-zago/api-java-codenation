package com.codenation.services;

import com.codenation.models.User;
import com.codenation.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.management.InstanceAlreadyExistsException;
import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    final private UserRepository userRepository;

    @Override
    public User register(User user) throws InstanceAlreadyExistsException {
        String email = user.getEmail();
        User checkUser = this.userRepository.findByEmail(email).orElse(null);
        if (checkUser == null) {
            return save(user);
        } else {
            throw new InstanceAlreadyExistsException("Usuário já cadastrado");
        }
    }

    @Override
    public User save(User object) {
        return this.userRepository.save(object);
    }

    @Override
    public List<User> getAll() {
        return this.userRepository.findAll();
    }
}
