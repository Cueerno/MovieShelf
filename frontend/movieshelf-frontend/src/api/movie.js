const API_BASE = 'http://localhost:8080/api/v1/movies';

export async function movieByImdbId(imdbId) {
    const token = localStorage.getItem('token');

    const res = await fetch(`${API_BASE}/${imdbId}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`,
        },
    });

    if (!res.ok) {
        throw new Error(`Error ${res.status}: ${await res.text()}`);
    }

    return await res.json();
}
