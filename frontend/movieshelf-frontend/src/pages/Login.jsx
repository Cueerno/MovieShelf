import {useState} from 'react';
import {useNavigate} from "react-router-dom";
import {useAuth} from "../context/AuthContext";

export default function Login() {
    const [form, setForm] = useState({username: '', password: ''});
    const [status, setStatus] = useState('');
    const navigate = useNavigate();
    const {login} = useAuth();

    const handleChange = (e) => {
        setForm({...form, [e.target.name]: e.target.value});
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setStatus('⏳ Вход...');

        try {
            const response = await fetch('http://localhost:8080/api/v1/users/auth/login', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(form),
            });

            if (!response.ok) {
                throw new Error('Bad credentials');
            }

            const data = await response.json();

            login(data.token);

            setStatus('✅ Success');

            setTimeout(() => navigate('/'), 1000);
        } catch (err) {
            console.error(err);
            setStatus('❌ Log In error');
        }
    };

    return (
        <div style={{padding: '2rem', maxWidth: '400px', margin: 'auto'}}>
            <h2>🔐 Log In</h2>
            <form onSubmit={handleSubmit} style={{display: 'flex', flexDirection: 'column', gap: '1rem'}}>
                <input
                    type="text"
                    name="username"
                    placeholder="Username"
                    value={form.username}
                    onChange={handleChange}
                    required
                />
                <input
                    type="password"
                    name="password"
                    placeholder="Password"
                    value={form.password}
                    onChange={handleChange}
                    required
                />
                <button type="submit">Войти</button>
                <p>{status}</p>
            </form>
        </div>
    );
}

