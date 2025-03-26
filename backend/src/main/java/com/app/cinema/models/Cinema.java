package com.app.cinema.models;

import lombok.Data;

import java.util.Set;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.*;

@Entity
@Table(name = "cinema")
@Data
@DynamicUpdate
public class Cinema {
        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        @Column(name = "id")
        private Long id;

        @Column(name = "name")
        private String name;

        @Column(name = "adress")
        private String address;

        @ManyToMany(cascade = CascadeType.REMOVE)
        @JoinTable(name = "cinema_to_movie", joinColumns = { @JoinColumn(name = "cinema_id") }, inverseJoinColumns = {
                        @JoinColumn(name = "movie_id") })
        private Set<Movie> setOfMovies;
}
