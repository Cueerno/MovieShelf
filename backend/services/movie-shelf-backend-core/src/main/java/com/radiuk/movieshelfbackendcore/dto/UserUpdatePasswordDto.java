package com.radiuk.movieshelfbackendcore.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdatePasswordDto {

    private String oldPassword;

    private String newPassword;
}
