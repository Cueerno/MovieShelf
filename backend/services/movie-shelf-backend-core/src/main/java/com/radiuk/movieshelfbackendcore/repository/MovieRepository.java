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
        exists(select 1 from favorites f where f.movie_id = m.id and f.user_id = :userId) is_favorite,
        count(distinct f.user_id) favorite_count,
        cast(avg(r.score) as smallint) average_rating,
        count(distinct c.id) comment_count,
        count(distinct rl.user_id) likes_count,
        count(distinct rd.user_id) dislikes_count
    from movie m
    left join favorites f on f.movie_id = m.id
    left join rating r on r.movie_id = m.id
    left join comment c on c.movie_id = m.id
    left join reaction rl on rl.movie_id = m.id and rl.like_type = 'LIKE'
    left join reaction rd on rd.movie_id = m.id and rd.like_type = 'DISLIKE'
    where m.imdb_id = :imdbId
    group by m.id;
    """, nativeQuery = true)
    AdditionalMovieInformation findAdditionalMovieInformationByMovieImdbId(String imdbId, Long userId);
}
