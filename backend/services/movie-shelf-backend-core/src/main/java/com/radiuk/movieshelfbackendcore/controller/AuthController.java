package com.radiuk.movieshelfbackendcore.controller;

import com.radiuk.movieshelfbackendcore.dto.UserAuthDto;
import com.radiuk.movieshelfbackendcore.dto.UserRegistrationDto;
import com.radiuk.movieshelfbackendcore.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/users/auth")
@RequiredArgsConstructor
@CrossOrigin(
        origins = "*",
        allowedHeaders = "*",
        exposedHeaders = "Authorization",
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.DELETE}
)
public class AuthController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody UserRegistrationDto userRegistrationDto) {
        userService.register(userRegistrationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "User created"));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserAuthDto userAuthDto) {
        return ResponseEntity.ok(Map.of("token", userService.getToken(userAuthDto)));
    }
}
