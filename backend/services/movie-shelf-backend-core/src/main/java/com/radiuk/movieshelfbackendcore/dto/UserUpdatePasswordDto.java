package com.radiuk.movieshelfbackendcore.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdatePasswordDto {

    private String currentPassword;

    private String newPassword;
}
