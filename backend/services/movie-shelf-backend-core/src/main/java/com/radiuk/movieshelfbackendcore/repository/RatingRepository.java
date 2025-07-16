package com.radiuk.movieshelfbackendcore.repository;

import com.radiuk.movieshelfbackendcore.model.Rating;
import com.radiuk.movieshelfbackendcore.model.id.RatingId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating, RatingId> {


}
