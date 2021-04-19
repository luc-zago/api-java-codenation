package com.codenation.services;

import com.codenation.models.Level;
import com.codenation.repositories.LevelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LevelServiceImpl implements LevelService {

    final private LevelRepository levelRepository;

    @Override
    public Level save(Level object) {
        return this.levelRepository.save(object);
    }

    public List<Level> getAll() { return this.levelRepository.findAll(); }

}
