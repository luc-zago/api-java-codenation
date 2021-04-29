package com.codenation.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CreateEventDTO {

    private Long id;
    private String description;
    private String origin;
    private LocalDate date;
    private Integer quantity;
    private String level;

}
