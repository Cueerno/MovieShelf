export async function signup(form) {
    const res = await fetch('http://localhost:8080/api/v1/users/auth/signup', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(form),
    });

    if (!res.ok) throw new Error('Error');

    return await res.json();
}

export async function loginUser(form) {
    const res = await fetch('http://localhost:8080/api/v1/users/auth/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(form),
    });

    if (!res.ok) throw new Error('Error');

    return await res.json();
}
