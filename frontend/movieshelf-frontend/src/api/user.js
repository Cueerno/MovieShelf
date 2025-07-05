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
        throw new Error('Ошибка загрузки');
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