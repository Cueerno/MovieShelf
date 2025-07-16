package com.radiuk.movieshelfbackendcore.controller;

import com.radiuk.movieshelfbackendcore.dto.RatingDto;
import com.radiuk.movieshelfbackendcore.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rating")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @PostMapping("/{imdbId}")
    public ResponseEntity<?> addRating(@PathVariable("imdbId") String imdbId, @RequestBody RatingDto ratingDto, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(ratingService.addRating(imdbId, ratingDto, userDetails.getUsername()));
    }

    @DeleteMapping("/{imdbId}")
    public ResponseEntity<?> deleteRating(@PathVariable("imdbId") String imdbId, @AuthenticationPrincipal UserDetails userDetails) {
        ratingService.deleteRatting(imdbId, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
}
