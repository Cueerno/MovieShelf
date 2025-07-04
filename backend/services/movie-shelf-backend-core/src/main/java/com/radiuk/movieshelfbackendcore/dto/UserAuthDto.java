package com.radiuk.movieshelfbackendcore.dto;

import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAuthDto {

    private String username;

    private String password;
}
