package com.radiuk.movieshelfbackendcore.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "movie")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String year;

    @Column(name = "imdb_id")
    private String imdbId;

    private String type;

    private String poster;

    private String rated;

    private String released;

    private String runtime;

    private String genre;

    private String director;

    private String writer;

    private String actors;

    private String plot;

    private String language;

    private String country;

    private String awards;

    private String metascore;

    @Column(name = "imdb_rating")
    private String imdbRating;

    @Column(name = "imdb_votes")
    private String imdbVotes;

    private String dvd;

    @Column(name = "box_office")
    private String boxOffice;

    private String production;

    private String website;

    @OneToMany(mappedBy = "movie", orphanRemoval = true)
    private List<MovieRating> ratings;

    @OneToMany(mappedBy = "movie",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private Set<Favorite> favorites = new HashSet<>();

    @OneToMany(mappedBy = "movie",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private Set<Reaction> reactions = new HashSet<>();

    @OneToMany(mappedBy = "movie",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();
}