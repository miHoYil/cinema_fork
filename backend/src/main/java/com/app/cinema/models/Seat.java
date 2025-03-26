package com.app.cinema.models;

import lombok.Data;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
@Table(name = "seat")
@Data
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "row_number")
    private Integer rowNumber;

    @Column(name = "number")
    private Integer number;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "hall_id", nullable = false)
    @JsonIgnore
    private Hall hall;
}
