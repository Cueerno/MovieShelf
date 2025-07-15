package com.radiuk.movieshelfbackendcore.service;

import com.radiuk.movieshelfbackendcore.client.OmdbClient;
import com.radiuk.movieshelfbackendcore.dto.OmdbFullMovieDto;
import com.radiuk.movieshelfbackendcore.dto.OmdbSearchResponse;
import com.radiuk.movieshelfbackendcore.mapper.MovieMapper;
import com.radiuk.movieshelfbackendcore.model.*;
import com.radiuk.movieshelfbackendcore.model.id.FavoriteId;
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

    public OmdbSearchResponse searchByTitle(String query, Short year, String type, Byte page) {
        return omdbClient.searchMovies(API_KEY, query, year, type, page);
    }

    @Transactional
    public OmdbFullMovieDto findByImdbId(String imdbId, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(EntityNotFoundException::new);
        Optional<Movie> optionalMovie = movieRepository.findByImdbId(imdbId);

        OmdbFullMovieDto omdbFullMovieDto;

        if (optionalMovie.isPresent()) {
            Movie movie = optionalMovie.get();

            omdbFullMovieDto = movieMapper.movieToOmdbFullMovieDto(movie);
            omdbFullMovieDto.setIsUserFavorite(favoriteRepository.existsByUserAndMovie(user, movie));
        }
        else {
            omdbFullMovieDto = omdbClient.getMovieByImdbId(API_KEY, imdbId);
            omdbFullMovieDto.setIsUserFavorite(false);
        }

        return omdbFullMovieDto;
    }


    @Transactional
    public void addToFavorites(String imdbId, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(EntityNotFoundException::new);

        Movie movie = movieRepository.findByImdbId(imdbId).orElse(null);

        if (movie == null) {
            OmdbFullMovieDto omdbFullMovieDto = findByImdbId(imdbId,  username);
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
    public List<OmdbFullMovieDto> getFavorites(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(EntityNotFoundException::new);

        return favoriteRepository.findAllByUser(user).stream()
                .map(Favorite::getMovie)
                .map(movieMapper::movieToOmdbFullMovieDto)
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

    public List<OmdbFullMovieDto> getTopRatedMovies() {
        Pageable topFive = PageRequest.of(0, 5);
        return movieMapper.movieListToOmdbFullMovieDtoList(movieRepository.findTopMoviesFavoritedByMultipleUsers(topFive));
    }
}
