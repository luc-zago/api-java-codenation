package com.codenation.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotEmpty
    @Column(nullable = false)
    private String description;

    @NotEmpty
    @Column(nullable = false)
    private String log;

    @NotEmpty
    @Column(nullable = false, length = 100)
    private String origin;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDate date;

    @Column(nullable = false)
    private Integer quantity;

    @NotNull
    @ManyToOne
    private User user;

    @NotNull
    @ManyToOne
    private Level level;

}
