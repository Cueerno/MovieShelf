package com.radiuk.movieshelfbackendcore.dto;

import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDto {

    private String username;

    private String email;
}
