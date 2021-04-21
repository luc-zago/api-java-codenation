package com.codenation.models;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotEmpty(message = "O campo 'email' é obrigatório")
    @Email(message = "O campo 'email' precisa ter um formato de email válido")
    @Column(length = 50)
    private String email;

    @NotEmpty(message = "O campo 'nome' é obrigatório")
    @Column(length = 50)
    private String firstname;

    @NotEmpty(message = "O campo 'sobrenome' é obrigatório")
    @Column(length = 100)
    private String lastname;

    @NotEmpty(message = "O campo 'senha' é obrigatório")
    @Column(length = 100)
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    private String password;

}
