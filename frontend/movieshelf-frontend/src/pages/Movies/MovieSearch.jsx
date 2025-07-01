import { useState } from 'react';
import SearchBar from '../../components/movie/SearchBar';
import MovieList from '../../components/movie/MovieList';
import { searchMovies } from '../../api/movies';

export default function MovieSearch() {
    const [query, setQuery] = useState('');
    const [movies, setMovies] = useState([]);
    const [status, setStatus] = useState('');

    const handleSearch = async () => {
        setStatus('🔎 Search...');
        try {
            const data = await searchMovies(query);
            setMovies(data);
            setStatus(data.length === 0 ? '🙁 Nothing...' : '');
        } catch (err) {
            console.error(err);
            setStatus('❌ Error loading');
        }
    };

    return (
        <div style={{ padding: '20px', textAlign: 'center' }}>
            <h2>Movie seaching</h2>
            <SearchBar query={query} setQuery={setQuery} onSearch={handleSearch} />
            <p>{status}</p>
            <MovieList movies={movies} />
        </div>
    );
}
