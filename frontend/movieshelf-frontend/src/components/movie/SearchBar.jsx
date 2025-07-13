import React from 'react';

function SearchBar({ query, setQuery, year, setYear, type, setType, onSearch }) {
    return (
        <div
            style={{
                display: 'flex',
                flexDirection: 'row',
                flexWrap: 'wrap',
                alignItems: 'center',
                justifyContent: 'center',
                gap: '10px',
                marginBottom: '20px',
            }}
        >
            <input
                type="text"
                value={query}
                placeholder="Enter movie name"
                onChange={(e) => setQuery(e.target.value)}
                onKeyDown={(e) => e.key === 'Enter' && onSearch(1)}
                style={{ padding: '8px', width: '200px' }}
            />
            <input
                type="text"
                value={year || ''}
                placeholder="Year (optional)"
                onChange={(e) => setYear(e.target.value ? parseInt(e.target.value) : undefined)}
                style={{ padding: '8px', width: '120px' }}
            />
            <select
                value={type}
                onChange={(e) => setType(e.target.value)}
                style={{ padding: '8px', width: '140px' }}
            >
                <option value="">All types</option>
                <option value="movie">Movie</option>
                <option value="series">Series</option>
                <option value="episode">Episode</option>
            </select>
            <button onClick={() => onSearch(1)} style={{ padding: '8px 16px' }}>
                Search
            </button>
        </div>
    );
}

export default SearchBar;
