package com.radiuk.movieshelfbackendcore.mapper;


import com.radiuk.movieshelfbackendcore.dto.MovieSearchDto;
import com.radiuk.movieshelfbackendcore.model.Movie;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    Movie movieDtoToMovie(MovieSearchDto movieSearchDto);

    MovieSearchDto movieToMovieSearchDto(Movie movie);
}
