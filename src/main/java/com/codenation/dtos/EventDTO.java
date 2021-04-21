package com.codenation.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
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
