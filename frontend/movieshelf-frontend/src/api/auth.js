export async function signup(form) {
    const res = await fetch('http://localhost:8080/api/v1/users/auth/signup', {
        method: 'POST',
        headers: {'Content-Type': 'application/json'},
        body: JSON.stringify(form),
    });

    const data = await res.json();

    if (!res.ok) {
        const messages = Array.isArray(data.messages) ? data.messages : [data.message || 'Registration error'];
        throw new Error(messages.join('\n'));
    }

    return data;
}

export async function loginUser(form) {
    const res = await fetch('http://localhost:8080/api/v1/users/auth/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(form),
    });

    const data = await res.json();

    if (!res.ok) {
        const messages = Array.isArray(data.messages) ? data.messages : [data.message || 'Log In error'];
        throw new Error(messages.join('\n'));
    }
    return data;
}
