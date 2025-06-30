import { useParams } from 'react-router-dom';
import { useEffect, useState } from 'react';

export default function MovieDetail() {
    const { movieId } = useParams();
    const [movie, setMovie] = useState(null);
    const [error, setError] = useState('');

    useEffect(() => {
        fetch(`https://www.omdbapi.com/?apikey=7aa1d4b5&i=${movieId}`)
            .then((res) => {
                if (!res.ok) throw new Error('Movie loading error');
                return res.json();
            })
            .then(setMovie)
            .catch((err) => setError(err.message));
    }, [movieId]);

    if (error) return <p style={{ color: 'red' }}>❌ {error}</p>;
    if (!movie) return <p>Loading...</p>;

    return (
        <div style={{ padding: '2rem', display: 'flex', gap: '2rem', alignItems: 'flex-start' }}>
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
                onError={(e) =>
                    (e.target.src = 'https://via.placeholder.com/220x320?text=No+Poster')
                }
            />
            <div>
                <h2 style={{ marginBottom: '0.5rem' }}>{movie.title}</h2>
                <p><strong>Год:</strong> {movie.year}</p>
                <p><strong>Тип:</strong> {movie.type?.charAt(0).toUpperCase() + movie.type?.slice(1)}</p>
                {/* Здесь можно добавить описание, рейтинг и т.д. */}
            </div>
        </div>
    );
}
