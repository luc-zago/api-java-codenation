package com.codenation.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
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
