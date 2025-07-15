package com.radiuk.movieshelfbackendcore.service;

import com.radiuk.movieshelfbackendcore.dto.CommentDto;
import com.radiuk.movieshelfbackendcore.dto.CommentRequestDto;
import com.radiuk.movieshelfbackendcore.mapper.CommentMapper;
import com.radiuk.movieshelfbackendcore.model.Comment;
import com.radiuk.movieshelfbackendcore.model.Movie;
import com.radiuk.movieshelfbackendcore.model.User;
import com.radiuk.movieshelfbackendcore.repository.CommentRepository;
import com.radiuk.movieshelfbackendcore.repository.MovieRepository;
import com.radiuk.movieshelfbackendcore.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final CommentMapper commentMapper;

    public List<CommentDto> findAllCommentsByMovie(String imdbId) {
        return commentMapper.toDtoList(commentRepository.findAllByImdbId(imdbId));
    }

    @Transactional
    public CommentDto saveComment(String imdbId, CommentRequestDto commentRequestDto, String username) {
        Comment comment = commentMapper.commentRequestDtoToComment(commentRequestDto);

        User user = userRepository.findByUsername(username).orElseThrow(EntityNotFoundException::new);
        Movie movie = movieRepository.findByImdbId(imdbId).orElseThrow(EntityNotFoundException::new);

        comment.setUser(user);
        comment.setMovie(movie);
        comment.setCreatedAt(OffsetDateTime.now());
        comment.setUpdatedAt(OffsetDateTime.now());

        Comment newComment = commentRepository.save(comment);

        return commentMapper.commentToCommentDto(newComment);
    }

    @Transactional
    public void updateComment(Long commentId, CommentDto commentDto, String username) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(EntityNotFoundException::new);

        if (!comment.getUser().getUsername().equals(username)) {
            throw new AccessDeniedException("You can't edit someone else's comment.");
        }

        comment.setText(commentDto.getText());
        comment.setUpdatedAt(OffsetDateTime.now());

        commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(Long commentId, String username) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(EntityNotFoundException::new);

        if (!comment.getUser().getUsername().equals(username)) {
            throw new AccessDeniedException("You can't delete someone else's comment.");
        }

        commentRepository.deleteById(commentId);
    }
}
