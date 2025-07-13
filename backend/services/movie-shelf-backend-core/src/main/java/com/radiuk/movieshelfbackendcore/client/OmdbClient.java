package com.radiuk.movieshelfbackendcore.client;

import com.radiuk.movieshelfbackendcore.dto.MovieSearchDto;
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
            @RequestParam(name = "page", required = false, defaultValue = "1") Byte page
    );

    @GetMapping("/")
    MovieSearchDto getMovieByImdbId(
            @RequestParam("apikey") String apiKey,
            @RequestParam("i") String imdbId
    );
}
