package com.codenation.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class EventDTOWithLog {

    private Long id;
    private String description;
    private String log;
    private String origin;
    private Date date;
    private Integer quantity;
    private String user;
    private String level;

}
