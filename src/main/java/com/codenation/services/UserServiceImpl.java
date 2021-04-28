package com.codenation.services;

import com.codenation.enums.Authority;
import com.codenation.models.User;
import com.codenation.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.InstanceAlreadyExistsException;
import java.util.List;
import java.util.NoSuchElementException;
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

    private String getLoggedUserEmail() {
        String email;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            email = ((UserDetails)principal).getUsername();
        } else {
            email = principal.toString();
        }
        return email;
    }

    @Override
    public User register(User user) throws InstanceAlreadyExistsException {
        Optional<User> checkUser = userRepository.findByEmail(user.getEmail());

        if (checkUser.isPresent()) {
            throw new InstanceAlreadyExistsException("Usuário já cadastrado");
        } else {
            user.setPassword(this.passwordEncoder().encode(user.getPassword()));
            user.setAuthority(Authority.USER);
            return userRepository.save(user);
        }
    }

    @Override
    public User update(User user) {
        String email = getLoggedUserEmail();
        User oldUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado"));
        if (!email.equals(user.getEmail())) {
            throw new IllegalArgumentException("Usuário inválido");
        }
        oldUser.setFirstname(user.getFirstname());
        oldUser.setLastname(user.getLastname());
        oldUser.setPassword(this.passwordEncoder().encode(user.getPassword()));
        return userRepository.save(oldUser);
    }

    @Override
    public User save(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

}
