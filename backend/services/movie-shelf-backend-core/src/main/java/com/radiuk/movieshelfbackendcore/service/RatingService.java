package com.radiuk.movieshelfbackendcore.service;

import com.radiuk.movieshelfbackendcore.dto.RatingDto;
import com.radiuk.movieshelfbackendcore.mapper.RatingMapper;
import com.radiuk.movieshelfbackendcore.model.Movie;
import com.radiuk.movieshelfbackendcore.model.Rating;
import com.radiuk.movieshelfbackendcore.model.User;
import com.radiuk.movieshelfbackendcore.model.id.RatingId;
import com.radiuk.movieshelfbackendcore.repository.MovieRepository;
import com.radiuk.movieshelfbackendcore.repository.RatingRepository;
import com.radiuk.movieshelfbackendcore.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;
    private final RatingMapper ratingMapper;

    @Transactional
    public RatingDto addRating(String imdbId, RatingDto ratingDto, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(EntityNotFoundException::new);
        Movie movie = movieRepository.findByImdbId(imdbId).orElseThrow(EntityNotFoundException::new);

        RatingId id = new RatingId(user.getId(), movie.getId());

        Rating rating = ratingRepository.findById(id)
                .orElse(Rating.builder()
                        .id(id)
                        .user(user)
                        .movie(movie)
                        .createdAt(OffsetDateTime.now())
                        .build()
                );

        rating.setScore(ratingDto.getScore());
        rating.setUpdatedAt(OffsetDateTime.now());

        Rating ratingSaved = ratingRepository.save(rating);

        return ratingMapper.toDto(ratingSaved);
    }

    @Transactional
    public void deleteRatting(String imdbId, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(EntityNotFoundException::new);
        Movie movie = movieRepository.findByImdbId(imdbId).orElseThrow(EntityNotFoundException::new);

        ratingRepository.deleteById(new RatingId(user.getId(), movie.getId()));
    }
}
