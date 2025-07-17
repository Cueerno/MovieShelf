package com.radiuk.movieshelfbackendcore.service;

import com.radiuk.movieshelfbackendcore.client.OmdbClient;
import com.radiuk.movieshelfbackendcore.dto.AdditionalMovieInformation;
import com.radiuk.movieshelfbackendcore.dto.OmdbFullMovieDto;
import com.radiuk.movieshelfbackendcore.dto.OmdbSearchResponse;
import com.radiuk.movieshelfbackendcore.mapper.MovieMapper;
import com.radiuk.movieshelfbackendcore.model.*;
import com.radiuk.movieshelfbackendcore.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService {

    @Value("${api_key}")
    private String API_KEY;

    private final OmdbClient omdbClient;
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final FavoriteRepository favoriteRepository;
    private final MovieMapper movieMapper;

    public OmdbSearchResponse searchByTitle(String query, Short year, String type, Byte page) {
        return omdbClient.searchMovies(API_KEY, query, year, type, page);
    }

    @Transactional
    public OmdbFullMovieDto findByImdbId(String imdbId, String username) {
        User user = userRepository.findByUsername(username).orElseThrow(EntityNotFoundException::new);
        Optional<Movie> optionalMovie = movieRepository.findByImdbId(imdbId);

        OmdbFullMovieDto omdbFullMovieDto;
        AdditionalMovieInformation additionalMovieInformation = new AdditionalMovieInformation();

        if (optionalMovie.isPresent()) {
            Movie movie = optionalMovie.get();

            omdbFullMovieDto = movieMapper.movieToOmdbFullMovieDto(movie);
            additionalMovieInformation.setIsUserFavorite(favoriteRepository.existsByUserAndMovie(user, movie));
        } else {
            omdbFullMovieDto = omdbClient.getMovieByImdbId(API_KEY, imdbId);
            additionalMovieInformation.setIsUserFavorite(false);
        }


        omdbFullMovieDto.setAdditionalMovieInformation(movieRepository.findAdditionalMovieInformationByMovieImdbId(imdbId));

        return omdbFullMovieDto;
    }

    public List<OmdbFullMovieDto> getTopRatedMovies() {
        Pageable topFive = PageRequest.of(0, 5);
        return movieMapper.movieListToOmdbFullMovieDtoList(movieRepository.findTopMoviesFavoritedByMultipleUsers(topFive));
    }
}
