const token = localStorage.getItem('token');

export async function addRating(imdbId, rating) {
    const res = await fetch(`http://localhost:8080/api/v1/rating/${imdbId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(rating),
    });

    if (!res.ok) throw new Error(`Error ${res.status}: ${await res.text()}`);

    return await res.json();
}

export async function deleteRating(imdbId) {
    const res = await fetch(`http://localhost:8080/api/v1/rating/${imdbId}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`,
        },
    });

    if (!res.ok) throw new Error(`Error ${res.status}: ${await res.text()}`);
}