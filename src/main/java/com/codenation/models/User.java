package com.codenation.models;

import com.codenation.enums.Authority;
import com.codenation.enums.UserStatus;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "USERS")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Size(min = 5, message = "A senha deve ter no mínimo 5 caracteres")
    private String password;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

}
