package com.codenation.services;

import com.codenation.models.Level;
import com.codenation.repositories.LevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.InstanceAlreadyExistsException;
import java.util.List;

@Service
public class LevelServiceImpl implements LevelService {

    private final LevelRepository levelRepository;

    @Autowired
    public LevelServiceImpl(LevelRepository levelRepository) {
        this.levelRepository = levelRepository;
    }

    @Override
    public Level register(Level level) throws InstanceAlreadyExistsException {
        String description = level.getDescription();
        Level checkLevel = levelRepository.findByDescription(description).orElse(null);
        if (checkLevel == null) {
            return levelRepository.save(level);
        } else {
            throw new InstanceAlreadyExistsException("Level já cadastrado");
        }    }

    @Override
    public List<Level> getAll() { return levelRepository.findAll(); }

}
