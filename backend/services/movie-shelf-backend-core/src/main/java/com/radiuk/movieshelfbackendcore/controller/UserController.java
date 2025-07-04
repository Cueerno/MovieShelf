package com.radiuk.movieshelfbackendcore.controller;

import com.radiuk.movieshelfbackendcore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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

}
