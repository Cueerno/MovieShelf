package com.radiuk.movieshelfbackendcore.controller;

import com.radiuk.movieshelfbackendcore.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/favorite")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @PostMapping("/{imdbId}")
    public ResponseEntity<?> addToFavorites(@PathVariable String imdbId, @AuthenticationPrincipal UserDetails userDetails) {
        favoriteService.addToFavorites(imdbId, userDetails.getUsername());
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<?> getFavorites(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(favoriteService.getFavorites(userDetails.getUsername()));
    }

    @DeleteMapping("/{imdbId}")
    public ResponseEntity<?> removeFavorites(@PathVariable String imdbId, @AuthenticationPrincipal UserDetails userDetails) {
        favoriteService.removeFromFavorites(imdbId, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
}
