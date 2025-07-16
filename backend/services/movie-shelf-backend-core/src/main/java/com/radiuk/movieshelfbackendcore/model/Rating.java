package com.radiuk.movieshelfbackendcore.model;

import com.radiuk.movieshelfbackendcore.model.id.RatingId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.OffsetDateTime;

@Getter
@Setter
@Entity
@Table(name = "rating")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Rating {
    @EmbeddedId
    private RatingId id;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "user_id")
    private User user;

    @MapsId("movieId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "movie_id")
    private Movie movie;

    private Byte score;

    @NotNull
    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @NotNull
    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

}