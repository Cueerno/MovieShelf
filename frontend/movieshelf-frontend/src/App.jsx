import React, { useState } from "react";
import SearchBar from "./components/SearchBar";
import MovieList from "./components/MovieList";

function App() {
    const [query, setQuery] = useState("");
    const [movies, setMovies] = useState([]);
    const [status, setStatus] = useState("");

    const searchMovies = async () => {
        setStatus("🔎 Searching...");
        try {
            const res = await fetch(
                `http://localhost:8080/api/v1/movies/search?query=${encodeURIComponent(query)}`
            );
            const data = await res.json();
            setMovies(data);
            setStatus(data.length === 0 ? "🙁 Nothing..." : "");
        } catch (error) {
            console.error(error);
            setStatus("❌ Error loading");
        }
    };

    return (
        <div style={{ padding: "20px", fontFamily: "sans-serif", textAlign: "center" }}>
            <h1>🎬 MovieShelf</h1>
            <SearchBar query={query} setQuery={setQuery} onSearch={searchMovies} />
            <p>{status}</p>
            <MovieList movies={movies} />
        </div>
    );
}

export default App;
