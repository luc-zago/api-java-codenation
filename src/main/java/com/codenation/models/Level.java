package com.codenation.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Getter
@Setter
@Table(name = "LEVELS")
public class Level {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "O campo 'descrição' é obrigatório")
    @Column(length = 15, message = "O campo 'descrição' não pode ter mais de 15 caracteres")
    private String description;

}
