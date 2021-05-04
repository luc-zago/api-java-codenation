package com.codenation.services;

import com.codenation.models.User;
import org.springframework.data.domain.Pageable;

import javax.management.InstanceAlreadyExistsException;
import java.util.List;

public interface UserService {

    User register(User user) throws InstanceAlreadyExistsException;
    User update(User user);
    List<User> getAll(String email, String firstName, String lastName, String status,
                      String order, String sort, Integer page, Integer size, Pageable pageable);
    User changeAuthority(Long id, String authority);
}
