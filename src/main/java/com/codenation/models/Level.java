package com.codenation.models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Data
@Table(name = "levels")
public class Level {

    @Id
    private Long id;

    @Column(nullable = false, length = 10)
    private String description;

}
