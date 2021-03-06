package com.codenation.models;

import com.fasterxml.jackson.annotation.*;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(name = "EVENTS")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Min(value = 1, message = "O campo 'quantidade' não pode ser inferior à 1")
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    @NotNull(message = "O campo 'level' é obrigatório")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Level level;

    @JsonProperty("level")
    private void LevelConverter (Long levelId) {
        this.level = new Level();
        level.setId(levelId);
    }

}
