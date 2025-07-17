package com.radiuk.movieshelfbackendcore.service;

import com.radiuk.movieshelfbackendcore.dto.OmdbFullMovieDto;
import com.radiuk.movieshelfbackendcore.dto.OmdbShortMovieDto;
import com.radiuk.movieshelfbackendcore.mapper.MovieMapper;
import com.radiuk.movieshelfbackendcore.model.Favorite;
import com.radiuk.movieshelfbackendcore.model.Movie;
import com.radiuk.movieshelfbackendcore.model.MovieRating;
import com.radiuk.movieshelfbackendcore.model.User;
import com.radiuk.movieshelfbackendcore.model.id.FavoriteId;
import com.radiuk.movieshelfbackendcore.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final FavoriteRepository favoriteRepository;
    private final MovieRatingRepository movieRatingRepository;
    private final MovieMapper movieMapper;
    private final MovieService movieService;

    @Transactional
    public void addToFavorites(String imdbId, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(EntityNotFoundException::new);

        Movie movie = movieRepository.findByImdbId(imdbId).orElse(null);

        if (movie == null) {
            OmdbFullMovieDto omdbFullMovieDto = movieService.findByImdbId(imdbId, username);
            movie = movieRepository.save(movieMapper.movieDtoToMovie(omdbFullMovieDto));

            if (omdbFullMovieDto.getRatings() != null) {
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
    public List<OmdbShortMovieDto> getFavorites(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(EntityNotFoundException::new);

        return favoriteRepository.findAllByUser(user).stream()
                .map(Favorite::getMovie)
                .map(movieMapper::movieToOmdbShortMovieDto)
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
}
