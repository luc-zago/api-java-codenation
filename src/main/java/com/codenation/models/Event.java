package com.codenation.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Data
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @NotBlank
    @Column(nullable = false)
    private String description;

    @NotBlank
    @Column(nullable = false)
    private String log;

    @NotBlank
    @Column(nullable = false, length = 100)
    private String origin;

    @NotBlank
    @Column(nullable = false)
    private LocalDateTime date;

    @NotBlank
    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne
    private User user;

    @ManyToOne
    private Level level;

}
