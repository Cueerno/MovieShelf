const token = localStorage.getItem('token');

export async function getAllCommentsByMovie(imdbId) {
    const res = await fetch(`http://localhost:8080/api/v1/comment/${imdbId}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`,
        },
    });

    if (!res.ok) throw new Error(`Error ${res.status}: ${await res.text()}`);

    return await res.json();
}

export async function addComment(imdbId, text) {
    const res = await fetch(`http://localhost:8080/api/v1/comment/${imdbId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(text),
    });

    if (!res.ok) throw new Error(`Error ${res.status}: ${await res.text()}`);

    return await res.json();
}

export async function updateComment(commentId, text) {
    const res = await fetch(`http://localhost:8080/api/v1/comment/${commentId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`,
        },
        body: JSON.stringify(text),
    });

    if (!res.ok) throw new Error(`Error ${res.status}: ${await res.text()}`);

    return await res.json();
}

export async function deleteComment(commentId) {
    const res = await fetch(`http://localhost:8080/api/v1/comment/${commentId}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`,
        },
    });

    if (!res.ok) throw new Error(`Error ${res.status}: ${await res.text()}`);

    return await res.json();
}