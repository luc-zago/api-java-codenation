package com.codenation.services;

import com.codenation.models.Level;

import java.util.List;

public interface LevelService extends ServiceInterface<Level> {

    public List<Level> getAll();
}
