import {useState} from 'react';
import {useNavigate} from "react-router-dom";
import {signup} from "../api/auth";

export default function Signup() {
    const [form, setForm] = useState({username: '', email: '', password: ''});
    const [status, setStatus] = useState('');
    const navigate = useNavigate();

    const handleChange = (e) => {
        setForm({...form, [e.target.name]: e.target.value});
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setStatus('⏳ Registration...');

        try {
            await signup(form);

            setStatus('✅ Registrated');
            setForm({username: '', email: '', password: ''});

            setTimeout(() => navigate('/login'), 1000);
        } catch (err) {
            console.error(err);
            setStatus('❌ Registration error');
        }
    };

    return (<div style={{padding: '2rem', maxWidth: '400px', margin: 'auto'}}>
        <h2>📝 Registration</h2>
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
                type="email"
                name="email"
                placeholder="Email"
                value={form.email}
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
            <button type="submit">Sign Up</button>
            <p>{status}</p>
        </form>
    </div>);
}
