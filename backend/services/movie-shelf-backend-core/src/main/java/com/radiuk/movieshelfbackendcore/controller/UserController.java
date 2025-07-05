package com.radiuk.movieshelfbackendcore.controller;

import com.radiuk.movieshelfbackendcore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<?> getUserByUsername() {
        return ResponseEntity.ok(userService.findByUsername(userService.getUserDetails().getUsername()));
    }

    @PostMapping("/avatar")
    public ResponseEntity<?> uploadAvatar(@RequestParam("file") MultipartFile file, @AuthenticationPrincipal UserDetails userDetails) throws IOException {
        userService.updateAvatarUrl(userDetails.getUsername(), file);
        return ResponseEntity.ok().build();
    }
}
