package com.radiuk.movieshelfbackendcore.mapper;

import com.radiuk.movieshelfbackendcore.dto.CommentResponseDto;
import com.radiuk.movieshelfbackendcore.dto.CommentRequestDto;
import com.radiuk.movieshelfbackendcore.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(source = "user.username", target = "username")
    CommentResponseDto commentToCommentDto(Comment comment);

    List<CommentResponseDto> toDtoList(List<Comment> comments);

    Comment commentRequestDtoToComment(CommentRequestDto commentRequestDto);
}
