export async function searchMovies(query) {
    const res = await fetch(
        `http://localhost:8080/api/v1/movies/search?query=${encodeURIComponent(query)}`
    );
    if (!res.ok) {
        throw new Error(`Ошибка ${res.status}: ${await res.text()}`);
    }
    return res.json();
}
