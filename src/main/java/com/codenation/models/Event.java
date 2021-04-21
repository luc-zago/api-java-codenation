package com.codenation.models;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotEmpty(message = "O campo 'descrição' é obrigatório")
    @Size(max = 255, message = "O campo 'descrição' não pode ter mais de 255 caracteres")
    private String description;

    @NotEmpty(message = "O campo 'log' é obrigatório")
    @Size(max = 255, message = "O campo 'log' não pode ter mais de 255 caracteres")
    private String log;

    @NotEmpty(message = "O campo 'origem' é obrigatório")
    @Column(length = 100)
    @Size(max = 100, message = "O campo 'origem' não pode ter mais de 100 caracteres")
    private String origin;

    @NotNull(message = "O campo 'data' é obrigatório")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDate date;

    @NotNull(message = "O campo 'quantidade' é obrigatório")
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    @NotNull(message = "O campo 'level' é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "levels_id")
    private Level level;
/*
    @JsonProperty("user")
    private void UserConverter (String email) {
        this.user = new User();
        user.setEmail(email);
    } */

    @JsonProperty("level")
    private void LevelConverter (Long levelId) {
        this.level = new Level();
        level.setId(levelId);
    }

}
