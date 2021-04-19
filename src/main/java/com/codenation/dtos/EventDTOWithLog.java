package com.codenation.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class EventDTOWithLog {

    private Long id;
    private String description;
    private String log;
    private String origin;
    private LocalDate date;
    private Integer quantity;
    private String userEmail;
    private String levelDescription;

}
