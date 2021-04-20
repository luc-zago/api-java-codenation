package com.codenation.dtos;

import lombok.Data;

@Data
public class UserDTOWithId {

    private Long id;
    private String email;
    private String firstname;
    private String lastname;
}
