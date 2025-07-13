import {useState} from 'react';
import SearchBar from '../../components/movie/SearchBar';
import MovieList from '../../components/movie/MovieList';
import {searchMovies} from '../../api/movies';

export default function MovieSearch() {
    const [query, setQuery] = useState('');
    const [year, setYear] = useState('');
    const [type, setType] = useState('');
    const [movies, setMovies] = useState([]);
    const [status, setStatus] = useState('');
    const [page, setPage] = useState(1);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState('');
    const [totalResults, setTotalResults] = useState(0);

    const handleSearch = async (newPage = 1) => {
        setLoading(true);
        setError('');
        setStatus('🔎 Searching...');

        try {
            const data = await searchMovies(query, year, type || undefined, newPage);
            const results = Array.isArray(data.search) ? data.search : [];

            setMovies(results);
            setTotalResults(parseInt(data.totalResults || '0', 10));
            setPage(newPage);
            setStatus(results.length === 0 ? '🙁 Nothing found' : '');
        } catch (err) {
            console.error(err);
            setError('❌ Error loading');
            setStatus('');
        } finally {
            setLoading(false);
        }
    };

    const maxPage = Math.ceil(totalResults / 10);

    return (
        <div style={{ padding: '20px', textAlign: 'center' }}>
            <h2>Movie Search</h2>

            <SearchBar
                query={query}
                setQuery={setQuery}
                year={year}
                setYear={setYear}
                type={type}
                setType={setType}
                onSearch={handleSearch}
            />

            <p>{status}</p>
            <MovieList movies={movies} />

            {!loading && !error && movies.length > 0 && (
                <div style={{ marginTop: '20px', display: 'flex', justifyContent: 'center', gap: '10px' }}>
                    <button disabled={page <= 1} onClick={() => handleSearch(page - 1)}>
                        ⬅ Prev
                    </button>
                    <span>Page {page} of {maxPage}</span>
                    <button disabled={page >= maxPage} onClick={() => handleSearch(page + 1)}>
                        Next ➡
                    </button>
                </div>
            )}
        </div>
    );
}

