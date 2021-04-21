package com.codenation.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateEventDTO {

    private Long id;
    private String description;
    private String origin;
    private LocalDate date;
    private Integer quantity;
    private String user;
    private String levelDescription;

}