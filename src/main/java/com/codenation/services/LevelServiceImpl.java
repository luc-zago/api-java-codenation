package com.codenation.services;

import com.codenation.models.Level;
import com.codenation.repositories.LevelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.management.InstanceAlreadyExistsException;
import java.util.List;

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
        Level checkLevel = this.levelRepository.findByDescription(description);
        if (checkLevel == null) {
            return save(level);
        } else {
            throw new InstanceAlreadyExistsException("Level já cadastrado");
        }    }

    public List<Level> checkList(List<Level> levelList) {
        if (levelList.isEmpty()) {
            throw new IllegalArgumentException("Nenhum level encontrado");
        }
        return levelList;
    }

    @Override
    public List<Level> getAll() { return checkList(this.levelRepository.findAll()); }

}
