package com.radiuk.movieshelfbackendcore.mapper;

import com.radiuk.movieshelfbackendcore.dto.MovieDto;
import com.radiuk.movieshelfbackendcore.model.Movie;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    MovieDto movieToMovieDto(Movie movie);

    List<MovieDto> movieListToMovieDtoList(List<Movie> movies);
}
