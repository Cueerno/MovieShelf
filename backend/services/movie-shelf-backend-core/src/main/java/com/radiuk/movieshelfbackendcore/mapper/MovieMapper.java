package com.radiuk.movieshelfbackendcore.mapper;


import com.radiuk.movieshelfbackendcore.dto.OmdbFullMovieDto;
import com.radiuk.movieshelfbackendcore.model.Movie;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    Movie movieDtoToMovie(OmdbFullMovieDto omdbFullMovieDto);

    OmdbFullMovieDto movieToOmdbFullMovieDto(Movie movie);

    List<OmdbFullMovieDto> movieListToOmdbFullMovieDtoList(List<Movie> movies);
}
