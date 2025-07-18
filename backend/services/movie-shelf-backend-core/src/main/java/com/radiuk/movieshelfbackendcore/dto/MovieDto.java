package com.radiuk.movieshelfbackendcore.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MovieDto {

    private String title;

    private String year;

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

    private String poster;

    private List<OmdbMovieRatingDto> ratings;

    private String metascore;

    private String imdbRating;

    private String imdbVotes;

    private String imdbId;

    private String type;

    private String dvd;

    private String boxOffice;

    private String production;

    private String website;

    private AdditionalMovieInformation additionalMovieInformation;
}
