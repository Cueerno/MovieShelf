package com.radiuk.movieshelfbackendcore.controller;

import com.radiuk.movieshelfbackendcore.dto.CommentDto;
import com.radiuk.movieshelfbackendcore.dto.CommentRequestDto;
import com.radiuk.movieshelfbackendcore.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comment")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{imdbId}")
    public ResponseEntity<?> getAllCommentsByMovie(@PathVariable String imdbId) {
        return ResponseEntity.ok(commentService.findAllCommentsByMovie(imdbId));
    }

    @PostMapping("/{imdbId}")
    public ResponseEntity<?> addComment(@PathVariable String imdbId, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.addComment(imdbId, commentRequestDto, userDetails.getUsername()));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable Long commentId, @RequestBody CommentDto commentDto, @AuthenticationPrincipal UserDetails userDetails) {
        commentService.updateComment(commentId, commentDto, userDetails.getUsername());
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetails userDetails) {
        commentService.deleteComment(commentId, userDetails.getUsername());
        return ResponseEntity.noContent().build();
    }
}