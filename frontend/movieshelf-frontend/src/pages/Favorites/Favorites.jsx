import { useEffect, useState } from 'react';
import {getFavorites} from "../../api/favorites";
import MovieList from "../../components/movie/MovieList";

export default function FavoritesPage () {
    const [favorites, setFavorites] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');

    useEffect(() => {
        getFavorites()
            .then(setFavorites)
            .catch((err) => setError(err.message))
            .finally(() => setLoading(false));
    }, []);

    if (error) return <p style={{color: 'red'}}>❌ {error}</p>;
    if (loading) return <p>Loading favorites...</p>;

    return (
        <div style={{ textAlign: 'center' }}>
            <h1>Favorite Movies</h1>
            {favorites.length === 0 ? (
                <p>You don't have any favorite movies yet.</p>
            ) : (
                <div className="movie-grid">
                    <MovieList movies={favorites}/>
                </div>
            )}
        </div>
    );
};
