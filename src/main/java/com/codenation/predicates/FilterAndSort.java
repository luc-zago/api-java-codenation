package com.codenation.predicates;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class FilterAndSort {

    private String description;
    private String origin;
    private LocalDate date;
    private Integer quantity;
    private String email;
    private String levelDescription;
    private String order;
    private String sort;
}
