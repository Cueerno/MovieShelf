package com.radiuk.movieshelfbackendcore.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.radiuk.movieshelfbackendcore.client.OmdbClient;
import com.radiuk.movieshelfbackendcore.dto.ListMoviesSearchDto;
import com.radiuk.movieshelfbackendcore.dto.MovieSearchDto;
import com.radiuk.movieshelfbackendcore.dto.OmdbSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    @Value("${api_key}")
    private String API_KEY;

    private final OmdbClient omdbClient;

    public List<ListMoviesSearchDto> searchMovies(String query) {
        OmdbSearchResponse response = omdbClient.searchMovies(API_KEY, query);

        if (response == null || response.getSearch() == null) {
            return List.of();
        }

        return response.getSearch();
    }

    public MovieSearchDto getMovieByImdbId(String imdbId) {
        return omdbClient.getMovieByImdbId(API_KEY, imdbId);
    }
}
