package com.radiuk.movieshelfbackendcore.controller;

import com.radiuk.movieshelfbackendcore.service.UserService;
import lombok.RequiredArgsConstructor;
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
}
