import React from "react";

function SearchBar({ query, setQuery, onSearch }) {
    return (
        <div>
            <input
                type="text"
                value={query}
                placeholder="Enter the movie title"
                onChange={(e) => setQuery(e.target.value)}
                style={{ padding: "8px", width: "250px", marginRight: "10px" }}
            />
            <button onClick={onSearch} style={{ padding: "8px 16px" }}>
                Найти
            </button>
        </div>
    );
}

export default SearchBar;
