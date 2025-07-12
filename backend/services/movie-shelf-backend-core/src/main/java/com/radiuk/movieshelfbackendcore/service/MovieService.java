package com.radiuk.movieshelfbackendcore.service;

import com.radiuk.movieshelfbackendcore.client.OmdbClient;
import com.radiuk.movieshelfbackendcore.dto.ListMoviesSearchDto;
import com.radiuk.movieshelfbackendcore.dto.MovieSearchDto;
import com.radiuk.movieshelfbackendcore.dto.OmdbSearchResponse;
import com.radiuk.movieshelfbackendcore.mapper.MovieMapper;
import com.radiuk.movieshelfbackendcore.model.*;
import com.radiuk.movieshelfbackendcore.repository.FavoriteRepository;
import com.radiuk.movieshelfbackendcore.repository.MovieRatingRepository;
import com.radiuk.movieshelfbackendcore.repository.MovieRepository;
import com.radiuk.movieshelfbackendcore.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService {

    @Value("${api_key}")
    private String API_KEY;

    private final OmdbClient omdbClient;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final FavoriteRepository favoriteRepository;
    private final MovieRatingRepository movieRatingRepository;
    private final MovieMapper movieMapper;

    public List<ListMoviesSearchDto> searchByTitle(String query) {
        OmdbSearchResponse response = omdbClient.searchMovies(API_KEY, query);

        if (response == null || response.getSearch() == null) {
            return List.of();
        }

        return response.getSearch();
    }

    @Transactional
    public MovieSearchDto findByImdbId(String imdbId, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(EntityNotFoundException::new);
        Optional<Movie> optionalMovie = movieRepository.findByImdbId(imdbId);

        MovieSearchDto movieSearchDto;

        if (optionalMovie.isPresent()) {
            Movie movie = optionalMovie.get();

            movieSearchDto = movieMapper.movieToMovieSearchDto(movie);
            movieSearchDto.setIsUserFavorite(favoriteRepository.existsByUserAndMovie(user, movie));
        }
        else {
            movieSearchDto = omdbClient.getMovieByImdbId(API_KEY, imdbId);
            movieSearchDto.setIsUserFavorite(false);
        }

        return movieSearchDto;
    }


    @Transactional
    public void addToFavorites(String imdbId, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(EntityNotFoundException::new);

        Movie movie = movieRepository.findByImdbId(imdbId).orElse(null);

        if (movie == null) {
            MovieSearchDto movieSearchDto = findByImdbId(imdbId,  username);
            movie = movieRepository.save(movieMapper.movieDtoToMovie(movieSearchDto));

            if (movieSearchDto.getRatings() != null) {
                Movie finalMovie = movie;
                List<MovieRating> ratings = movieSearchDto.getRatings().stream()
                        .map(r -> MovieRating.builder()
                                .movie(finalMovie)
                                .source(r.getSource())
                                .value(r.getValue())
                                .build())
                        .toList();

                movieRatingRepository.saveAll(ratings);
            }
        }

        if (favoriteRepository.existsByUserAndMovie(user, movie)) {
            return;
        }

        Favorite favorite = new Favorite();
        favorite.setId(new FavoriteId(user.getId(), movie.getId()));
        favorite.setUser(user);
        favorite.setMovie(movie);
        favorite.setCreatedAt(OffsetDateTime.now());

        favoriteRepository.save(favorite);
    }

    @Transactional
    public List<MovieSearchDto> getFavorites(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(EntityNotFoundException::new);

        return favoriteRepository.findAllByUser(user).stream()
                .map(Favorite::getMovie)
                .map(movieMapper::movieToMovieSearchDto)
                .toList();
    }

    @Transactional
    public void removeFromFavorites(String imdbId, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(EntityNotFoundException::new);
        Movie movie = movieRepository.findByImdbId(imdbId).orElseThrow(EntityNotFoundException::new);

        favoriteRepository.deleteByUserAndMovie(user, movie);

        boolean stillFavorite = favoriteRepository.existsByMovie((movie));

        if (!stillFavorite) {
            movieRepository.delete(movie);
        }
    }

    public List<MovieSearchDto> getTopRatedMovies() {
        Pageable topFive = PageRequest.of(0, 5);

        return movieMapper.movieListToMovieSearchDtoList(movieRepository.findTopMoviesFavoritedByMultipleUsers(topFive));
    }
}
