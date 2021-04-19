package com.codenation.services;

import com.codenation.models.Level;

import javax.management.InstanceAlreadyExistsException;
import java.util.List;

public interface LevelService extends ServiceInterface<Level> {

    public Level register(Level level) throws InstanceAlreadyExistsException;
    public List<Level> getAll();
}
