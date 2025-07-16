package com.radiuk.movieshelfbackendcore.mapper;

import com.radiuk.movieshelfbackendcore.dto.RatingDto;
import com.radiuk.movieshelfbackendcore.model.Rating;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RatingMapper {

    RatingDto toDto(Rating rating);
}
