package com.radiuk.movieshelfbackendcore.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;

import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OmdbSearchResponse {
    @JsonAlias("Search")
    private List<OmdbShortMovieDto> search;

    private String totalResults;
}
