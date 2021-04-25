package com.codenation.services;

import com.codenation.models.Level;
import com.codenation.models.User;

import javax.management.InstanceAlreadyExistsException;
import java.util.List;

public interface UserService extends ServiceInterface<User> {

    public User register(User user) throws InstanceAlreadyExistsException;
    public void deleteById(Long id);
    public List<User> getAll();
}
