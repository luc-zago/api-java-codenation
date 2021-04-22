package com.codenation.repositories;

import com.codenation.models.Level;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LevelRepository extends JpaRepository<Level, Long> {

    public Optional<Level> findByDescription(String description);
}
