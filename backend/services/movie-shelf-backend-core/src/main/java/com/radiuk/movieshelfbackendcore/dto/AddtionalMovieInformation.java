package com.radiuk.movieshelfbackendcore.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddtionalMovieInformation {

    private Boolean isUserFavorite;

    private long favoriteCount;

    private short averageRating;

    private long commentsCount;

    private long likesCount;

    private long dislikesCount;
}
