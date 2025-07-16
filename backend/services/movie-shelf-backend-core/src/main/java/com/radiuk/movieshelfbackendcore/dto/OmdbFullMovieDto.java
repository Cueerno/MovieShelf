package com.radiuk.movieshelfbackendcore.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class OmdbFullMovieDto {

    @JsonAlias("Title")
    private String title;

    @JsonAlias("Year")
    private String year;

    @JsonAlias("Rated")
    private String rated;

    @JsonAlias("Released")
    private String released;

    @JsonAlias("Runtime")
    private String runtime;

    @JsonAlias("Genre")
    private String genre;

    @JsonAlias("Director")
    private String director;

    @JsonAlias("Writer")
    private String writer;

    @JsonAlias("Actors")
    private String actors;

    @JsonAlias("Plot")
    private String plot;

    @JsonAlias("Language")
    private String language;

    @JsonAlias("Country")
    private String country;

    @JsonAlias("Awards")
    private String awards;

    @JsonAlias("Poster")
    private String poster;

    @JsonAlias("Ratings")
    private List<MovieRatingDto> ratings;

    @JsonAlias("Metascore")
    private String metascore;

    @JsonAlias("imdbRating")
    private String imdbRating;

    @JsonAlias("imdbVotes")
    private String imdbVotes;

    @JsonAlias("imdbID")
    private String imdbId;

    @JsonAlias("Type")
    private String type;

    @JsonAlias("DVD")
    private String dvd;

    @JsonAlias("BoxOffice")
    private String boxOffice;

    @JsonAlias("Production")
    private String production;

    @JsonAlias("Website")
    private String website;

    private Boolean isUserFavorite;

    private long commentsCount;

    private long likesCount;

    private long dislikesCount;
}