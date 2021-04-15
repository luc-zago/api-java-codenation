package com.codenation.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EventDTO {

    private Long id;
    private String description;
    private String origin;
    private LocalDateTime date;
    private Integer quantity;

}
