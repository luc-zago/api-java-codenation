package com.codenation.services;

import com.codenation.models.Event;
import com.codenation.models.Level;
import com.codenation.repositories.LevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.InstanceAlreadyExistsException;
import java.util.List;
import java.util.NoSuchElementException;

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
    public Level update(Level level, Long id) {
        Level oldLevel = levelRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Level não encontrado"));
        oldLevel.setDescription(level.getDescription());
        return levelRepository.save(oldLevel);
    }

    @Override
    public List<Level> getAll() { return this.levelRepository.findAll(); }

}
