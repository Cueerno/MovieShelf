package com.radiuk.movieshelfbackendcore.dto;

import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String username;

    private String email;

    private OffsetDateTime registeredAt;

}
