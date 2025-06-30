import React from 'react';

function SearchBar({ query, setQuery, onSearch }) {
    return (
        <div>
            <input
                type="text"
                value={query}
                placeholder="Enter film name"
                onChange={(e) => setQuery(e.target.value)}
                onKeyDown={(e) => e.key === 'Enter' && onSearch()}
                style={{ padding: '8px', width: '250px', marginRight: '10px' }}
            />
            <button onClick={onSearch} style={{ padding: '8px 16px' }}>
                Search
            </button>
        </div>
    );
}

export default SearchBar;
