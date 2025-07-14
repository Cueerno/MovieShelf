import {useParams} from 'react-router-dom';
import {useEffect, useState} from 'react';
import {movieByImdbId} from "../../api/movie";
import {addToFavorites, deleteFromFavorites} from "../../api/favorites";
import {FaStar} from "react-icons/fa";
import {useGlobalLoading} from '../../context/LoadingContext';

export default function Movie() {
    const {imdbId} = useParams();
    const [movie, setMovie] = useState(null);
    const [error, setError] = useState('');
    const [favError, setFavError] = useState('');
    const {setIsLoading} = useGlobalLoading();

    useEffect(() => {
        setIsLoading(true);
        movieByImdbId(imdbId)
            .then(setMovie)
            .catch((err) => setError(err.message))
            .finally(() => setIsLoading(false));
    }, [imdbId]);


    if (error) return <p style={{color: 'red'}}>❌ {error}</p>;

    const handleToggleFavorite = () => {
        if (!imdbId) return;

        const action = movie.isUserFavorite ? deleteFromFavorites : addToFavorites;

        action(imdbId)
            .then(() => {
                setMovie((prev) => ({...prev, isUserFavorite: !prev.isIserFavorite}));
            })
            .catch((err) => setFavError(err.message));
    }

    if (!movie && !error) return null;

    return (<div style={{padding: '2rem', display: 'flex', gap: '2rem', alignItems: 'flex-start'}}>
        <div style={{display: 'flex', flexDirection: 'column', alignItems: 'center'}}>
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
                onError={(e) => (e.target.src = '/assets/default-movie.png')}
            />
            <button
                onClick={handleToggleFavorite}
                style={{
                    backgroundColor: movie.isUserFavorite ? '#e50914' : '#555',
                    color: 'white',
                    padding: '0.6rem 1.2rem',
                    border: 'none',
                    borderRadius: '4px',
                    cursor: 'pointer',
                    display: 'flex',
                    alignItems: 'center',
                    gap: '0.5rem',
                    fontWeight: 'bold',
                    marginTop: '1rem'
                }}
            >
                <FaStar/>
                {movie.isUserFavorite ? 'Remove from favorites' : 'Add to favorites'}
            </button>
            {favError && <p style={{color: 'red', marginTop: '0.5rem'}}>❌ {favError}</p>}
        </div>

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
