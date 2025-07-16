package com.radiuk.movieshelfbackendcore.controller;

import com.radiuk.movieshelfbackendcore.dto.ReactionDto;
import com.radiuk.movieshelfbackendcore.service.ReactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reaction")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ReactionController {

    private final ReactionService reactionService;

    @PostMapping("/{imdbId}")
    public ResponseEntity<?> addReaction(@PathVariable String imdbId, @RequestBody ReactionDto reactionDto, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(reactionService.addReaction(imdbId, reactionDto, userDetails.getUsername()));
    }

    @DeleteMapping("/{imdbId}")
    public ResponseEntity<?> deleteReaction(@PathVariable String imdbId, @AuthenticationPrincipal UserDetails userDetails) {
        reactionService.deleteReaction(imdbId, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
}
