const API_BASE = 'http://localhost:8080/api/v1/movies/search?query=';

export async function searchMovies(query) {
    const res = await fetch(
        `${API_BASE}${encodeURIComponent(query)}`
    );
    if (!res.ok) {
        throw new Error(`Error ${res.status}: ${await res.text()}`);
    }
    return res.json();
}
