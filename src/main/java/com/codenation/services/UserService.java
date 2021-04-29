package com.codenation.services;

import com.codenation.models.User;

import javax.management.InstanceAlreadyExistsException;
import java.util.List;

public interface UserService {

    User register(User user) throws InstanceAlreadyExistsException;
    User update(User user);
    List<User> getAll();
}
