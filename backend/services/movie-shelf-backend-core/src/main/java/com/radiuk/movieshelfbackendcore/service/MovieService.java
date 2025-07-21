package com.radiuk.movieshelfbackendcore.service;

import com.radiuk.movieshelfbackendcore.client.OmdbClient;
import com.radiuk.movieshelfbackendcore.dto.*;
import com.radiuk.movieshelfbackendcore.mapper.MovieMapper;
import com.radiuk.movieshelfbackendcore.mapper.OmdbMovieMapper;
import com.radiuk.movieshelfbackendcore.model.*;
import com.radiuk.movieshelfbackendcore.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService {

    @Value("${api_key}")
    private String API_KEY;

    private final OmdbClient omdbClient;
    private final MovieRepository movieRepository;
    private final MovieRatingRepository movieRatingRepository;
    private final MovieMapper movieMapper;
    private final OmdbMovieMapper omdbMovieMapper;
    private final UserCacheService userCacheService;

    public OmdbSearchResponse searchByTitle(String query, Short year, String type, Byte page) {
        return omdbClient.searchMovies(API_KEY, query, year, type, page);
    }

    @Transactional(readOnly = true)
    public MovieDto findByImdbId(String imdbId, String username) {
        User user = userCacheService.getUserFromCache(username);

        Optional<Movie> optionalMovie = movieRepository.findByImdbId(imdbId);

        if (optionalMovie.isPresent()) {
            Movie movie = optionalMovie.get();
            MovieDto dto = movieMapper.movieToMovieDto(movie);

            AdditionalMovieInformation info = movieRepository.findAdditionalMovieInformationByMovieImdbId(imdbId, user.getId());

            if (info.getAverageRating() == null) {
                info.setAverageRating((short) 0);
            }

            dto.setAdditionalMovieInformation(info);

            return dto;
        }

        OmdbFullMovieDto omdbFullMovieDto = omdbClient.getMovieByImdbId(API_KEY, imdbId);
        return omdbMovieMapper.omdbFullMovieDtoToMovieDto(omdbFullMovieDto);
    }

    @Transactional
    public Movie getOrCreateMovie(String imdbId) {
        return movieRepository.findByImdbId(imdbId)
                .orElseGet(() -> fetchAndPersistMovie(imdbId));
    }

    private Movie fetchAndPersistMovie(String imdbId) {
        OmdbFullMovieDto omdbFullMovieDto = omdbClient.getMovieByImdbId(API_KEY, imdbId);

        Movie movie = omdbMovieMapper.omdbFullMovieDtoToMovie(omdbFullMovieDto);
        movie =  movieRepository.save(movie);

        if (omdbFullMovieDto.getRatings() != null && !omdbFullMovieDto.getRatings().isEmpty()) {
            Movie finalMovie = movie;
            List<MovieRating> ratings = omdbFullMovieDto.getRatings().stream()
                    .map(r -> MovieRating.builder()
                            .movie(finalMovie)
                            .source(r.getSource())
                            .value(r.getValue())
                            .build())
                    .toList();

            movieRatingRepository.saveAll(ratings);
        }

        return movie;
    }

    @Cacheable(value = "topMovies", key = "'top5'")
    @Transactional(readOnly = true)
    public List<MovieDto> getTopRatedMovies() {
        Pageable topFive = PageRequest.of(0, 5);
        List<Movie> top = movieRepository.findTopMoviesFavoritedByMultipleUsers(topFive);
        return movieMapper.movieListToMovieDtoList(top);
    }
}
