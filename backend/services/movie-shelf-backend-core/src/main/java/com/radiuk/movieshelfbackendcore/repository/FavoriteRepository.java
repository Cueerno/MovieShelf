package com.radiuk.movieshelfbackendcore.repository;

import com.radiuk.movieshelfbackendcore.model.Favorite;
import com.radiuk.movieshelfbackendcore.model.id.FavoriteId;
import com.radiuk.movieshelfbackendcore.model.Movie;
import com.radiuk.movieshelfbackendcore.model.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, FavoriteId> {

    boolean existsByUserAndMovie(User user, Movie movie);

    @EntityGraph(attributePaths = {"movie", "movie.ratings"})
    List<Favorite> findAllByUser(User user);

    void deleteByUserAndMovie(User user, Movie movie);

    boolean existsByMovie(Movie movie);
}
