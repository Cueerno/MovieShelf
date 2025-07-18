package com.radiuk.movieshelfbackendcore.mapper;

import com.radiuk.movieshelfbackendcore.dto.MovieDto;
import com.radiuk.movieshelfbackendcore.dto.OmdbFullMovieDto;
import com.radiuk.movieshelfbackendcore.model.Movie;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OmdbMovieMapper {

    Movie omdbFullMovieDtoToMovie(OmdbFullMovieDto omdbFullMovieDto);

    MovieDto omdbFullMovieDtoToMovieDto(OmdbFullMovieDto omdbFullMovieDto);

}
