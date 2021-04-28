package com.codenation.services;

import com.codenation.models.Level;
import com.codenation.models.User;

import javax.management.InstanceAlreadyExistsException;
import java.util.List;

public interface UserService {

    public User register(User user) throws InstanceAlreadyExistsException;
    public User update(User user);
    public List<User> getAll();
}
