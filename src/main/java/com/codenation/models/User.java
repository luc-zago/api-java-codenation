package com.codenation.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotEmpty
    @Column(nullable = false, length = 50)
    private String login;

    @NotEmpty
    @Column(nullable = false, length = 100)
    private String password;
/*
    @OneToMany
    private List<Event> eventList;*/

}
