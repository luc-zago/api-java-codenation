package com.codenation.services;

import com.codenation.enums.Authority;
import com.codenation.enums.UserStatus;
import com.codenation.models.User;
import com.codenation.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.InstanceAlreadyExistsException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final LoggedUser loggedUser;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, LoggedUser loggedUser) {
        this.userRepository = userRepository;
        this.loggedUser = loggedUser;
    }

    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public User register(User user) throws InstanceAlreadyExistsException {
        Optional<User> checkUser = userRepository.findByEmailAndStatus(user.getEmail(), UserStatus.ACTIVE);

        if (checkUser.isPresent()) {
            throw new InstanceAlreadyExistsException("Usuário já cadastrado");
        } else {
            user.setPassword(this.passwordEncoder().encode(user.getPassword()));
            user.setAuthority(Authority.USER);
            user.setStatus(UserStatus.ACTIVE);
            return userRepository.save(user);
        }
    }

    @Override
    public User update(User user) {
        String email = loggedUser.get().getEmail();
        User oldUser = userRepository.findByEmailAndStatus(email, UserStatus.ACTIVE)
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
    public List<User> getAll(String email, String firstName, String lastName, String status,
                             String order, String sort, Integer page, Integer size, Pageable pageable) {
        pageable = PageRequest.of(page, size, Sort.by(order).ascending());
        if (sort.equalsIgnoreCase("desc")) {
            pageable = PageRequest.of(page, size, Sort.by(order).descending());
        }
        UserStatus searchStatus = UserStatus.valueOf(status.toUpperCase());
        return userRepository.findAllByEmailContainsAndFirstnameContainsAndLastnameContainsAndStatus(email,
                firstName, lastName, searchStatus, pageable).getContent();
    }

    @Override
    public User changeAuthority(Long id, Authority authority) {
        User user = userRepository.findByIdAndStatus(id, UserStatus.ACTIVE)
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado"));
        User userLogged = userRepository.findByEmailAndStatus(loggedUser.get().getEmail(), UserStatus.ACTIVE)
                .orElse(null);
        if (!Objects.requireNonNull(userLogged).getAuthority().equals(Authority.ADMIN)) {
            throw new IllegalArgumentException("Usuário não autorizado");
        }
        user.setAuthority(authority);
        return userRepository.save(user);
    }

    public void inactiveById(Long id) {
        User user = userRepository.findByIdAndStatus(id, UserStatus.ACTIVE)
                .orElseThrow(() -> new NoSuchElementException("Usuário não encontrado"));
        User userLogged = userRepository.findByEmailAndStatus(loggedUser.get().getEmail(), UserStatus.ACTIVE)
                .orElse(null);
        if (!user.getEmail().equals(loggedUser.get().getEmail())
                && !Objects.requireNonNull(userLogged).getAuthority().equals(Authority.ADMIN)) {
            throw new IllegalArgumentException("Usuário não autorizado");
        }
        user.setStatus(UserStatus.INACTIVE);
        userRepository.save(user);
    }

}
