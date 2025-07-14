import {useEffect, useState} from 'react';
import {getFavorites} from "../../api/favorites";
import MovieList from "../../components/movie/MovieList";
import {useGlobalLoading} from '../../context/LoadingContext'

export default function FavoritesPage() {
    const [favorites, setFavorites] = useState([]);
    const {setIsLoading} = useGlobalLoading();
    const [error, setError] = useState('');

    useEffect(() => {
        setIsLoading(true);
        getFavorites()
            .then(setFavorites)
            .catch((err) => setError(err.message))
            .finally(() => setIsLoading(false));
    }, []);

    if (error) return <p style={{color: 'red'}}>❌ {error}</p>;

    return (<div style={{textAlign: 'center'}}>
        <h1>Favorite Movies</h1>
        {favorites.length === 0 ? (<p>You don't have any favorite movies yet.</p>) : (<div className="movie-grid">
            <MovieList movies={favorites}/>
        </div>)}
    </div>);
};
