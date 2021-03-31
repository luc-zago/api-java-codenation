package com.codenation.services;

import com.codenation.repositories.LevelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LevelServiceImpl implements LevelService{

    final private LevelRepository levelRepository;

    @Override
    public Object save(Object object) {
        return null;
    }
}
