package com.radiuk.movieshelfbackendcore.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ListMoviesSearchDto {

    @JsonAlias("Title")
    private String title;

    @JsonAlias("Year")
    private String year;

    @JsonAlias("imdbID")
    private String imdbId;

    @JsonAlias("Type")
    private String type;

    @JsonAlias("Poster")
    private String poster;
}
