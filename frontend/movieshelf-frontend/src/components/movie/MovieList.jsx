import React from 'react';
import MovieCard from './MovieCard';

function MovieList({ movies }) {
    return (
        <div
            style={{
                display: 'flex',
                justifyContent: 'center',
                flexWrap: 'wrap',
                gap: '20px',
                marginTop: '30px'
            }}
        >
            {movies.map((movie) => (
                <MovieCard key={movie.imdbID} movie={movie} />
            ))}
        </div>
    );
}

export default MovieList;
