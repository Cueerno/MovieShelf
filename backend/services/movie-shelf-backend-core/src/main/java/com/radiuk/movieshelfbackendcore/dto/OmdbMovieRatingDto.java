package com.radiuk.movieshelfbackendcore.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OmdbMovieRatingDto {

    @JsonAlias("Source")
    private String source;

    @JsonAlias("Value")
    private String value;
}