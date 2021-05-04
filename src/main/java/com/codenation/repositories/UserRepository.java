package com.codenation.repositories;

import com.codenation.enums.UserStatus;
import com.codenation.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByIdAndStatus(Long id, UserStatus status);
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndStatus(String email, UserStatus status);
    Page<User> findAllByEmailContainsAndFirstnameContainsAndLastnameContainsAndStatus(String email,
                                                                                      String firstname,
                                                                                      String lastname,
                                                                                      UserStatus status,
                                                                                      Pageable pageable);
}
