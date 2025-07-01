package com.radiuk.movieshelfbackendcore.controller;


import com.radiuk.movieshelfbackendcore.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/movies")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/search")
    public ResponseEntity<?> searchMovies(@RequestParam String query) {
        return ResponseEntity.ok(movieService.searchMovies(query));
    }

    @GetMapping("/{imdbId}")
    public ResponseEntity<?> getMovie(@PathVariable String imdbId) {
        return ResponseEntity.ok(movieService.getMovieByImdbId(imdbId));
    }
}