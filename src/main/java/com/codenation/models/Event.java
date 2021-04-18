package com.codenation.models;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotEmpty(message = "O campo 'descrição' é obrigatório")
    private String description;

    @NotEmpty(message = "O campo 'log' é obrigatório")
    private String log;

    @NotEmpty(message = "O campo 'origem' é obrigatório")
    @Column(length = 100)
    private String origin;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDate date;

    @NotNull(message = "O campo 'quantidade' é obrigatório")
    private Integer quantity;

    @NotNull(message = "O campo 'usuário' é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    @NotNull(message = "O campo 'level' é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Level level;

    @JsonProperty("user")
    private void UserConverter (Long userId) {
        this.user = new User();
        user.setId(userId);
    }

    @JsonProperty("level")
    private void LevelConverter (Long levelId) {
        this.level = new Level();
        level.setId(levelId);
    }

}
