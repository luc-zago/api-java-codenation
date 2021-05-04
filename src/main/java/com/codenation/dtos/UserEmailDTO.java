package com.codenation.dtos;

import com.codenation.enums.UserStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserEmailDTO {

    private String email;
    private UserStatus status;

}
