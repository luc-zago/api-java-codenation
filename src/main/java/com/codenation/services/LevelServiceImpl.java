package com.codenation.services;

import com.codenation.models.Event;
import com.codenation.models.Level;
import com.codenation.repositories.LevelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.management.InstanceAlreadyExistsException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class LevelServiceImpl implements LevelService {

    final private LevelRepository levelRepository;

    @Override
    public Level save(Level object) {
        return this.levelRepository.save(object);
    }

    @Override
    public Level register(Level level) throws InstanceAlreadyExistsException {
        String description = level.getDescription();
        Level checkLevel = this.levelRepository.findByDescription(description).orElse(null);
        if (checkLevel == null) {
            return save(level);
        } else {
            throw new InstanceAlreadyExistsException("Level já cadastrado");
        }    }

    @Override
    public Level update(Level level) {
        /*Level level = levelRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Level não encontrado")); */
        return level;
    }

    @Override
    public void deleteById(Long id) {
        Level level = levelRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Level não encontrado"));
        levelRepository.delete(level);
    }

    @Override
    public List<Level> getAll() { return this.levelRepository.findAll(); }

}
