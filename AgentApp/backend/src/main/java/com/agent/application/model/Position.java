package com.agent.application.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "positions")
@Getter
@Setter
public class Position {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name of position is mandatory!")
    private String name;

    @Column(name="pay", nullable = false)
    @Positive(message = "Pay is a positive value!")
    private Long pay;
}
