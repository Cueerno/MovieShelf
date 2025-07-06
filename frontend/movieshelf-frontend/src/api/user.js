export async function getUserData() {
    const token = localStorage.getItem('token');

    const res = await fetch('http://localhost:8080/api/v1/users/me', {
        method: 'GET', headers: {
            'Content-Type': 'application/json', Authorization: `Bearer ${token}`,
        },
    });

    if (!res.ok) {
        throw new Error(`Error ${res.status}: ${await res.text()}`);
    }

    return await res.json();
}

export async function uploadUserAvatar(file) {
    const formData = new FormData();
    formData.append('file', file);

    const res = await fetch('http://localhost:8080/api/v1/users/avatar', {
        method: 'POST',
        headers: {
            Authorization: `Bearer ${localStorage.getItem('token')}`,
        },
        body: formData,
    });

    if (!res.ok) {
        throw new Error('Loading error');
    }

    return await res.json();
}

export async function updateUser(data) {
    const res = await fetch('http://localhost:8080/api/v1/users/me', {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${localStorage.getItem('token')}`,
        },
        body: JSON.stringify(data),
    });

    if (!res.ok) {
        const text = await res.text();
        throw new Error(`Failed to update profile: ${text}`);
    }

    return await res.json();
}

export async function deleteUser() {
    const res = await fetch('http://localhost:8080/api/v1/users/delete', {
        method: 'DELETE',
        headers: {
            Authorization: `Bearer ${localStorage.getItem('token')}`,
        },
    })

    if (!res.ok) {
        const text = await res.text();
        throw new Error(`Error deleting: ${text}`);
    }
}