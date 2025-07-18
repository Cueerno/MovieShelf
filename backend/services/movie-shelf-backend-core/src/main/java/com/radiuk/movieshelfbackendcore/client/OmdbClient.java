package com.radiuk.movieshelfbackendcore.client;

import com.radiuk.movieshelfbackendcore.dto.OmdbFullMovieDto;
import com.radiuk.movieshelfbackendcore.dto.OmdbSearchResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "omdb", url = "https://www.omdbapi.com")
public interface OmdbClient {

    @GetMapping("/")
    OmdbSearchResponse searchMovies(
            @RequestParam("apikey") String apiKey,
            @RequestParam("s") String query,
            @RequestParam(name = "y", required = false) Short year,
            @RequestParam(name = "type", required = false) String type,
            @RequestParam(name = "page", required = false, defaultValue = "1") Byte page
    );

    @GetMapping("/")
    OmdbFullMovieDto getMovieByImdbId(
            @RequestParam("apikey") String apiKey,
            @RequestParam("i") String imdbId
    );
}
