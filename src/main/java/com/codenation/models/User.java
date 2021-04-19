package com.codenation.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotEmpty(message = "O campo 'email' é obrigatório")
    @Email(message = "O campo 'email' precisa ter um formato de email válido")
    @Column(length = 50)
    private String email;

    @NotEmpty(message = "O campo 'senha' é obrigatório")
    @Column(length = 100)
    private String password;

}
