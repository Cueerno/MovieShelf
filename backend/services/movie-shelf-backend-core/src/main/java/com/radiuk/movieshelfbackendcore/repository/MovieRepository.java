package com.radiuk.movieshelfbackendcore.repository;

import com.radiuk.movieshelfbackendcore.model.Movie;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    Optional<Movie> findByImdbId(String imdbId);

    @EntityGraph(attributePaths = "ratings")
    @Query("""
    select m from Movie m
    where m.id in (
        select f.movie.id
        from Favorite f
        group by f.movie.id
    )
    order by (
        select count(f2.user.id)
        from Favorite f2
        where f2.movie.id = m.id
    ) desc
    """)
    List<Movie> findTopMoviesFavoritedByMultipleUsers(Pageable pageable);

}
