package com.codenation.services;

import com.codenation.models.User;

import java.util.List;

public interface UserService extends ServiceInterface<User> {
    public List<User> getAll();
}
