package com.radiuk.movieshelfbackendcore.repository;

import com.radiuk.movieshelfbackendcore.dto.AdditionalMovieInformation;
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

    @EntityGraph(attributePaths = "ratings")
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

    @Query(value = """
    select
        (select exists(select 1 from favorites where movie_id = m.id)) is_favorite,
        (select count(*) from favorites where movie_id = m.id) favorites_count,
        (select cast(avg(score) as smallint) from rating where movie_id = m.id) average_rating,
        (select count(*) from comment where movie_id = m.id) comments_count,
        (select count(*) from reaction where movie_id = m.id and like_type = 'LIKE') likes_count,
        (select count(*) from reaction where movie_id = m.id and like_type = 'DISLIKE') dislikes_count
    from movie m
    where m.imdb_id = :imdbId
    """, nativeQuery = true)
    AdditionalMovieInformation findAdditionalMovieInformationByMovieImdbId(String imdbId);
}
