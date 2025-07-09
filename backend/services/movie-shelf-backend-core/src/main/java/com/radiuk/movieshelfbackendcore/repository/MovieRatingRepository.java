package com.radiuk.movieshelfbackendcore.repository;

import com.radiuk.movieshelfbackendcore.model.MovieRating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRatingRepository extends JpaRepository<MovieRating, Long> {
}