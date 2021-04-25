package com.codenation.services;

import com.codenation.models.Event;
import com.codenation.models.Level;

import javax.management.InstanceAlreadyExistsException;
import java.util.List;

public interface LevelService extends ServiceInterface<Level> {

    public Level register(Level level) throws InstanceAlreadyExistsException;
    public Level update(Level level);
    public void deleteById(Long id);
    public List<Level> getAll();
}
