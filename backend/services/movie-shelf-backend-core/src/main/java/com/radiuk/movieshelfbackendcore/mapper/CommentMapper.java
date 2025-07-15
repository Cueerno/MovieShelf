package com.radiuk.movieshelfbackendcore.mapper;

import com.radiuk.movieshelfbackendcore.dto.CommentDto;
import com.radiuk.movieshelfbackendcore.dto.CommentRequestDto;
import com.radiuk.movieshelfbackendcore.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mapping(source = "user.username", target = "username")
    CommentDto commentToCommentDto(Comment comment);

    List<CommentDto> toDtoList(List<Comment> comments);

    Comment commentRequestDtoToComment(CommentRequestDto commentRequestDto);
}
