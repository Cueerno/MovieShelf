package com.radiuk.movieshelfbackendcore.mapper;


import com.radiuk.movieshelfbackendcore.dto.MovieDto;
import com.radiuk.movieshelfbackendcore.dto.OmdbFullMovieDto;
import com.radiuk.movieshelfbackendcore.model.Movie;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovieMapper {

    Movie omdbFullMovieDtoToMovie(OmdbFullMovieDto omdbFullMovieDto);

    MovieDto movieToMovieDto(Movie movie);

    MovieDto omdbFullMovieDtoToMovieDto(OmdbFullMovieDto omdbFullMovieDto);

    List<MovieDto> movieListToMovieDtoList(List<Movie> movies);
}
