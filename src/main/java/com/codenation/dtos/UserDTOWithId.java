package com.codenation.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTOWithId {

    private Long id;
    private String email;
    private String firstname;
    private String lastname;
}
