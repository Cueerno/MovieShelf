package com.radiuk.movieshelfbackendcore.controller;

import com.radiuk.movieshelfbackendcore.dto.UserUpdateDto;
import com.radiuk.movieshelfbackendcore.dto.UserUpdatePasswordDto;
import com.radiuk.movieshelfbackendcore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<?> getUserByUsername(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(userService.findByUsername(userDetails.getUsername()));
    }

    @PostMapping("/avatar")
    public ResponseEntity<?> uploadAvatar(@RequestParam("file") MultipartFile file, @AuthenticationPrincipal UserDetails userDetails) throws IOException {
        return ResponseEntity.ok(Map.of("avatarUrl", userService.updateAvatarUrl(userDetails.getUsername(), file)));
    }

    @PatchMapping("/me")
    public ResponseEntity<?> update(@RequestBody UserUpdateDto userUpdateDto, @AuthenticationPrincipal UserDetails userDetails) {
        userService.update(userDetails.getUsername(), userUpdateDto);
        return ResponseEntity.ok(Map.of("message", "Profile updated"));
    }

    @PatchMapping("/password")
    public ResponseEntity<?> updatePassword(@RequestBody UserUpdatePasswordDto userUpdatePasswordDto, @AuthenticationPrincipal UserDetails userDetails) {
        userService.updatePassword(userUpdatePasswordDto, userDetails.getUsername());
        return ResponseEntity.ok(Map.of("message", "Password updated"));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteUser(@AuthenticationPrincipal UserDetails userDetails) {
        userService.deleteByUsername(userDetails.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(Map.of("message", "User deleted"));
    }
}
