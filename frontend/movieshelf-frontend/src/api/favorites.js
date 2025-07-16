const token = localStorage.getItem('token');

export async function getFavorites() {
    const res = await fetch(`http://localhost:8080/api/v1/favorite`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`,
        },
    });

    if (!res.ok) throw new Error(`Error ${res.status}: ${await res.text()}`);

    return await res.json();
}

export async function addToFavorites(imdbId) {
    const res = await fetch(`http://localhost:8080/api/v1/favorite/${imdbId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`,
        },
    });

    if (!res.ok) throw new Error(`Error ${res.status}: ${await res.text()}`);
}

export async function deleteFromFavorites(imdbId) {
    const res = await fetch(`http://localhost:8080/api/v1/favorite/${imdbId}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`,
        },
    });

    if (!res.ok) throw new Error(`Error ${res.status}: ${await res.text()}`);
}

