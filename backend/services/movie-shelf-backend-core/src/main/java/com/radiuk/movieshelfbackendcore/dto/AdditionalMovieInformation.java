package com.radiuk.movieshelfbackendcore.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdditionalMovieInformation {

    private Boolean isUserFavorite;

    private Long favoriteCount;

    private Short averageRating;

    private Long commentsCount;

    private Long likesCount;

    private Long dislikesCount;
}
