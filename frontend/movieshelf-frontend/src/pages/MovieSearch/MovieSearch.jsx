import { useState } from 'react';
import SearchBar from '../../components/movie/SearchBar';
import MovieList from '../../components/movie/MovieList';
import {searchMovies} from '../../api/movies';
import {useGlobalLoading} from '../../context/LoadingContext';

export default function MovieSearch() {
    const [query, setQuery] = useState('');
    const [year, setYear] = useState(undefined);
    const [type, setType] = useState('');
    const [movies, setMovies] = useState([]);
    const [status, setStatus] = useState('');
    const [page, setPage] = useState(1);
    const [error, setError] = useState('');
    const [totalResults, setTotalResults] = useState(0);
    const {setIsLoading} = useGlobalLoading();

    const [searchHistory, setSearchHistory] = useState(() => {
        try {
            const stored = localStorage.getItem('movieSearchHistory');
            return stored ? JSON.parse(stored) : [];
        } catch {
            return [];
        }
    });

    const handleSearch = async (newPage = 1) => {
        if (!query.trim()) return;

        setIsLoading(true);
        setError('');
        setStatus('🔎 Searching...');

        try {
            const data = await searchMovies(query, year, type || undefined, newPage);
            const results = Array.isArray(data.search) ? data.search : [];

            setMovies(results);
            setTotalResults(parseInt(data.totalResults || '0', 10));
            setPage(newPage);
            setStatus(results.length === 0 ? '🙁 Nothing found' : '');

            const newEntry = {query, year, type};
            const updatedHistory = [newEntry, ...searchHistory.filter((item) => item.query !== query || item.year !== year || item.type !== type),].slice(0, 5);

            setSearchHistory(updatedHistory);
            localStorage.setItem('movieSearchHistory', JSON.stringify(updatedHistory));
        } catch (err) {
            console.error(err);
            setError('❌ Error loading');
            setStatus('');
        } finally {
            setIsLoading(false);
        }
    };

    const maxPage = Math.ceil(totalResults / 10);

    return (
        <div style={{ padding: '20px', textAlign: 'center' }}>
            <h2>🎬 Movie Search</h2>

            <SearchBar
                query={query}
                setQuery={setQuery}
                year={year}
                setYear={setYear}
                type={type}
                setType={setType}
                onSearch={handleSearch}
            />

            {searchHistory.length > 0 && (
                <div style={{ marginBottom: '20px' }}>
                    <h4>🕘 Recent Searches</h4>
                    <ul style={{ listStyle: 'none', padding: 0 }}>
                        {searchHistory.map((item, index) => (
                            <li key={index}>
                                <button
                                    onClick={() => {
                                        setQuery(item.query);
                                        setYear(item.year);
                                        setType(item.type);
                                        handleSearch(1);
                                    }}
                                    style={{
                                        margin: '4px',
                                        padding: '6px 10px',
                                        border: '1px solid #ccc',
                                        borderRadius: '4px',
                                        background: '#f4f4f4',
                                        cursor: 'pointer',
                                    }}
                                >
                                    {item.query}
                                    {item.year ? `, ${item.year}` : ''}
                                    {item.type ? `, ${item.type}` : ''}
                                </button>
                            </li>
                        ))}
                    </ul>
                    <button
                        style={{
                            margin: '4px',
                            padding: '6px 10px',
                            border: '1px solid #ccc',
                            borderRadius: '4px',
                            background: '#f4f4f4',
                            cursor: 'pointer',
                        }}
                        onClick={() => {
                            setSearchHistory([]);
                            localStorage.removeItem('movieSearchHistory');
                        }}
                    >
                        🧹 Clear History
                    </button>
                </div>
            )}

        {error && <p style={{color: 'red'}}>{error}</p>}
        <p>{status}</p>
        <MovieList movies={movies}/>

        {!error && movies.length > 0 && (
            <div style={{marginTop: '20px', display: 'flex', justifyContent: 'center', gap: '10px'}}>
                <button disabled={page <= 1} onClick={() => handleSearch(page - 1)}>
                    ⬅ Prev
                </button>
                <span>Page {page} of {maxPage}</span>
                <button disabled={page >= maxPage} onClick={() => handleSearch(page + 1)}>
                    Next ➡
                </button>
            </div>)}
    </div>);
}
