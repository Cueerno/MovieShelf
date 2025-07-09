package com.radiuk.movieshelfbackendcore.repository;

import com.radiuk.movieshelfbackendcore.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    Optional<Movie> findByImdbId(String imdbId);
}
