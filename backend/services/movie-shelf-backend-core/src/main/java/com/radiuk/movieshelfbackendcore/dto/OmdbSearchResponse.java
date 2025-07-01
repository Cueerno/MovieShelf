package com.radiuk.movieshelfbackendcore.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OmdbSearchResponse {
    @JsonProperty("Search")
    private List<ListMoviesSearchDto> search;

}
