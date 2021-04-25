package com.codenation.services;

import com.codenation.models.Level;

import javax.management.InstanceAlreadyExistsException;
import java.util.List;

public interface LevelService {

    public Level register(Level level) throws InstanceAlreadyExistsException;
    public List<Level> getAll();
}
