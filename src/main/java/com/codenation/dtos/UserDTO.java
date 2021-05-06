package com.codenation.dtos;

import com.codenation.enums.Authority;
import com.codenation.enums.UserStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDTO {

    private String email;
    private String firstname;
    private String lastname;
    private Authority authority;
    private UserStatus status;

}
