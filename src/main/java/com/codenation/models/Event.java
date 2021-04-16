package com.codenation.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
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

    @NotNull
    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate date;

    @NotNull
    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne
    private User user;

    @ManyToOne
    private Level level;

}
