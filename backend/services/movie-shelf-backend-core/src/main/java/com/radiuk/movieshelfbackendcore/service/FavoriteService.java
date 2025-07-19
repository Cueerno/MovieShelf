package com.radiuk.movieshelfbackendcore.service;

import com.radiuk.movieshelfbackendcore.dto.MovieDto;
import com.radiuk.movieshelfbackendcore.mapper.MovieMapper;
import com.radiuk.movieshelfbackendcore.model.Favorite;
import com.radiuk.movieshelfbackendcore.model.Movie;
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
    private final FavoriteRepository favoriteRepository;
    private final MovieMapper movieMapper;
    private final MovieService movieService;
    private final UserCacheService userCacheService;

    @Transactional
    public void addToFavorites(String imdbId, String username) {
        User user = userCacheService.getUserFromCache(username);

        Movie movie = movieService.getOrCreateMovie(imdbId);

        if (favoriteRepository.existsByUserAndMovie(user, movie)) {
            return;
        }

        Favorite favorite = Favorite.builder()
                .id(new FavoriteId(user.getId(), movie.getId()))
                .user(user)
                .movie(movie)
                .createdAt(OffsetDateTime.now())
                .build();

        favoriteRepository.save(favorite);
    }

    @Transactional
    public List<MovieDto> getFavorites(String username) {
        User user = userCacheService.getUserFromCache(username);

        return favoriteRepository.findAllByUser(user).stream()
                .map(Favorite::getMovie)
                .map(movieMapper::movieToMovieDto)
                .toList();
    }

    @Transactional
    public void removeFromFavorites(String imdbId, String username) {
        User user =  userCacheService.getUserFromCache(username);
        Movie movie = movieRepository.findByImdbId(imdbId).orElseThrow(EntityNotFoundException::new);

        favoriteRepository.deleteByUserAndMovie(user, movie);

        boolean stillFavorite = favoriteRepository.existsByMovie((movie));

        if (!stillFavorite) {
            movieRepository.delete(movie);
        }
    }
}
