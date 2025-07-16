package com.radiuk.movieshelfbackendcore.mapper;

import com.radiuk.movieshelfbackendcore.dto.ReactionDto;
import com.radiuk.movieshelfbackendcore.model.Reaction;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ReactionMapper {

    ReactionDto toDto(Reaction reaction);
}