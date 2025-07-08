const token = localStorage.getItem('token');

export async function movieByImdbId(imdbId) {
    const res = await fetch(`http://localhost:8080/api/v1/movies/${imdbId}`, {
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
