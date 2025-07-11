export async function getTopMovies() {
    const res = await fetch(`http://localhost:8080/api/v1/movies/top`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
        },
    });

    if (!res.ok) {
        throw new Error(`Error ${res.status}: ${await res.text()}`);
    }

    return await res.json();
}
