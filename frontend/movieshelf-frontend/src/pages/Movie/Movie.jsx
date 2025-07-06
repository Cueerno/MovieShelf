import {useParams} from 'react-router-dom';
import {useEffect, useState} from 'react';
import {movieByImdbId} from "../../api/movie";

export default function Movie() {
    const {imdbId} = useParams();
    const [movie, setMovie] = useState(null);
    const [error, setError] = useState('');

    useEffect(() => {
        movieByImdbId(imdbId)
            .then(setMovie)
            .catch((err) => setError(err.message));
    }, [imdbId]);

    if (error) return <p style={{color: 'red'}}>❌ {error}</p>;
    if (!movie) return <p>Loading...</p>;

    return (<div style={{padding: '2rem', display: 'flex', gap: '2rem', alignItems: 'flex-start'}}>
        <img
            src={movie.poster}
            alt={movie.title}
            style={{
                width: '220px',
                height: '320px',
                objectFit: 'cover',
                borderRadius: '8px',
                boxShadow: '0 0 10px rgba(0,0,0,0.2)'
            }}
            onError={(e) => (e.target.src = 'https://via.placeholder.com/220x320?text=No+Poster')}
        />
        <div>
            <h2 style={{marginBottom: '0.5rem'}}>{movie.title}</h2>
            <p><strong>Year: </strong>{movie.year}</p>
            <p><strong>Type: </strong>{movie.type?.charAt(0).toUpperCase() + movie.type?.slice(1)}</p>
            <p><strong>Rated: </strong>{movie.rated}</p>
            <p><strong>Released: </strong>{movie.released}</p>
            <p><strong>Runtime: </strong>{movie.runtime}</p>
            <p><strong>Genre: </strong>{movie.genre}</p>
            <p><strong>Director: </strong>{movie.director}</p>
            <p><strong>Writer: </strong>{movie.writer}</p>
            <p><strong>Actors: </strong>{movie.actors}</p>
            <p><strong>Plot: </strong>{movie.plot}</p>
            <p><strong>Language: </strong>{movie.language}</p>
            <p><strong>Country: </strong>{movie.country}</p>
            <p><strong>Awards: </strong>{movie.awards}</p>
            <p><strong>Metascore: </strong>{movie.metascore}</p>
            <p><strong>IMDBRating: </strong>{movie.imdbRating}</p>
            <p><strong>IMDBVotes: </strong>{movie.imdbVotes}</p>
            <p><strong>DVD: </strong>{movie.dvd}</p>
            <p><strong>BoxOffice: </strong>{movie.boxOffice}</p>
            <p><strong>Production: </strong>{movie.production}</p>
            <p><strong>Website: </strong>{movie.website}</p>
            <div style={{marginTop: '1rem'}}>
                <h3>Ratings</h3>
                <ul style={{paddingLeft: '1.2rem'}}>
                    {movie.ratings?.map((rating, idx) => (<li key={idx}>
                        <strong>{rating.source}:</strong> {rating.value}
                    </li>))}
                </ul>
            </div>
        </div>
    </div>);
}
