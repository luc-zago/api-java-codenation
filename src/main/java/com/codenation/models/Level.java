package com.codenation.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity
@Data
@Table(name = "levels")
public class Level {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotEmpty(message = "O campo 'descrição' é obrigatório")
    @Column(length = 10)
    private String description;
/*
    @OneToMany
    private List<Event> eventList; */

}
