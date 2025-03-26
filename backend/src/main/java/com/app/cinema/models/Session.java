package com.app.cinema.models;

import lombok.Data;
import java.util.Set;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "session_movie")
@Data
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "start_session")
    private Instant start;

    @Column(name = "end_session")
    private Instant end;

    @ManyToMany(cascade = CascadeType.REMOVE)
    @JoinTable(name = "session_to_seat", joinColumns = { @JoinColumn(name = "session_id") }, inverseJoinColumns = {
            @JoinColumn(name = "seat_id") })
    private Set<Seat> listOfReservedSeats;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "cinema_id", nullable = false)
    private Cinema cinema;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "hall_id", nullable = false)
    private Hall hall;
}
