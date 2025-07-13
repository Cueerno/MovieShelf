const token = localStorage.getItem('token');

export async function searchMovies(query, year, type, page) {

    const params = new URLSearchParams({ query });

    if (year) params.append('year', year);
    if (type) params.append('type', type);
    if (page) params.append('page', page);

    const res = await fetch(
        `http://localhost:8080/api/v1/movies/search?${params.toString()}`,
        {
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
