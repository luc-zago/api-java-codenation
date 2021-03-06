package com.codenation.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class EventDTO {

    private Long id;
    private String description;
    private String origin;
    private LocalDate date;
    private Integer quantity;
    private String user;
    private String level;

}
