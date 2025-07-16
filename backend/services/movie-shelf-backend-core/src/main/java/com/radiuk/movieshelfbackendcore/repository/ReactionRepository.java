package com.radiuk.movieshelfbackendcore.repository;

import com.radiuk.movieshelfbackendcore.model.Reaction;
import com.radiuk.movieshelfbackendcore.model.id.ReactionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactionRepository extends JpaRepository<Reaction, ReactionId> {

    long countByMovieImdbIdAndReactionType(String imdbId, Reaction.ReactionType reactionType);
}