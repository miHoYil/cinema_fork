package com.app.cinema.models;

import lombok.Data;

import java.time.Duration;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.*;

@Entity
@Table(name = "movie")
@Data
@DynamicUpdate
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "time_duration")
    private Duration timeDuration;
}
