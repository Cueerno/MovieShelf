package com.radiuk.movieshelfbackendcore.dto;

import lombok.*;

import java.time.OffsetDateTime;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto {

    private Long id;

    private String text;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    private String username;
}
