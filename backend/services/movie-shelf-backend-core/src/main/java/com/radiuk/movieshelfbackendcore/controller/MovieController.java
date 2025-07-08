package com.radiuk.movieshelfbackendcore.controller;


import com.radiuk.movieshelfbackendcore.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/movies")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/search")
    public ResponseEntity<?> searchByTitle(@RequestParam String query) {
        return ResponseEntity.ok(movieService.searchByTitle(query));
    }

    @GetMapping("/{imdbId}")
    public ResponseEntity<?> getByImbdId(@PathVariable String imdbId, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(movieService.findByImdbId(imdbId, userDetails.getUsername()));
    }
}