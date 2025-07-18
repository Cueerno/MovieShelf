package com.radiuk.movieshelfbackendcore.service;

import com.radiuk.movieshelfbackendcore.dto.ReactionDto;
import com.radiuk.movieshelfbackendcore.mapper.ReactionMapper;
import com.radiuk.movieshelfbackendcore.model.Movie;
import com.radiuk.movieshelfbackendcore.model.Reaction;
import com.radiuk.movieshelfbackendcore.model.User;
import com.radiuk.movieshelfbackendcore.model.id.ReactionId;
import com.radiuk.movieshelfbackendcore.repository.MovieRepository;
import com.radiuk.movieshelfbackendcore.repository.ReactionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class ReactionService {

    private final ReactionRepository reactionRepository;
    private final MovieRepository movieRepository;
    private final ReactionMapper reactionMapper;
    private final MovieService movieService;
    private final UserCacheService userCacheService;

    @Transactional
    public ReactionDto addReaction(String imdbId, ReactionDto reactionDto, String username) {
        User user = userCacheService.getUserEntity(username);
        Movie movie = movieRepository.findByImdbId(imdbId).orElseGet(() -> movieService.getOrCreateMovie(imdbId));

        ReactionId id = new ReactionId(user.getId(), movie.getId());

        Reaction reaction = reactionRepository.findById(id)
                        .orElse(Reaction.builder()
                                .id(id)
                                .user(user)
                                .movie(movie)
                                .createdAt(OffsetDateTime.now())
                                .build()
                        );

        reaction.setReactionType(reactionDto.getReactionType());
        reaction.setUpdatedAt(OffsetDateTime.now());

        Reaction reactionSaved = reactionRepository.save(reaction);

        return reactionMapper.toDto(reactionSaved);
    }

    @Transactional
    public void deleteReaction(String imdbId, String username) {
        User user =  userCacheService.getUserEntity(username);
        Movie movie = movieRepository.findByImdbId(imdbId).orElseThrow(EntityNotFoundException::new);

        reactionRepository.deleteById(new ReactionId(user.getId(), movie.getId()));
    }
}
