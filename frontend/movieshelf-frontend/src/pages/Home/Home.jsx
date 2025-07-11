import MovieList from "../../components/movie/MovieList";
import {useEffect, useState} from "react";
import {getTopMovies} from "../../api/top";

export default function Home() {
    const [top, setTop] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState('');

    useEffect(() => {
        getTopMovies()
            .then(setTop)
            .catch((err) => setError(err.message))
            .finally(() => setLoading(false));
    }, []);

    if (error) return <p style={{color: 'red'}}>❌ {error}</p>;
    if (loading) return <p>Loading favorites...</p>;

    return (
        <div style={{ textAlign: 'center' }}>
            <h2>Welcome to MovieShelf</h2>
            <p>Jason Statham rules here💥</p>
            <img
                src="/assets/statham.png"
                alt="Jason Statham"
                style={{
                    width: '300px',
                    borderRadius: '8px',
                    boxShadow: '0 0 10px rgba(0,0,0,0.3)',
                    marginTop: '20px'
                }}
            />
            {top.length === 0 ? (
                <p></p>
            ) : (
                <div style={{ textAlign: 'center' }}>
                    <h1>Top Movies</h1>
                    <div className="movie-grid">
                        <MovieList movies={top}/>
                    </div>
                </div>
            )}
        </div>
    );
}