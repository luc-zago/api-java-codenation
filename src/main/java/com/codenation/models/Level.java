package com.codenation.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Data
@Table(name = "levels")
public class Level {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotEmpty
    @Column(nullable = false, length = 10)
    private String description;

    @OneToMany
    private List<Event> eventList;

}
