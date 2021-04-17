package com.codenation.models;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Entity
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotEmpty(message = "O campo 'email' é obrigatório")
    @Email(message = "O campo 'email' precisa ter um formato de email válido")
    @Column(nullable = false, length = 50)
    private String email;

    @NotEmpty(message = "O campo 'senha' é obrigatório")
    @Column(nullable = false, length = 100)
    private String password;
/*
    @OneToMany
    private List<Event> eventList;*/

}
