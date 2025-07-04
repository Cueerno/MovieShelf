package com.radiuk.movieshelfbackendcore.dto;

import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRegistrationDto {

    private String username;

    private String email;

    private String password;
}
