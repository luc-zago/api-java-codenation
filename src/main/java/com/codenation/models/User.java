package com.codenation.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Getter
@Setter
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank
    @Column(nullable = false, length = 50)
    private String login;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String password;

    @OneToMany
    private List<Event> eventList;

}
