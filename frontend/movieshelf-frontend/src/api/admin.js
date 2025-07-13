const token = localStorage.getItem('token');

export async function getAllUsers() {
    const res = await fetch(`http://localhost:8080/api/v1/admin/all-users`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            Authorization: `Bearer ${token}`,
        },
    });

    if (!res.ok) throw new Error(`Error ${res.status}: ${await res.text()}`);

    return await res.json();
}