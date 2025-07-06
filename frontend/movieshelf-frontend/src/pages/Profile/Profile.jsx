import {useEffect, useState} from 'react';
import {getUserData} from '../../api/user';
import {useNavigate} from "react-router-dom";

export default function Profile() {
    const [user, setUser] = useState(null);
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(true);
    const navigate = useNavigate();

    useEffect(() => {
        getUserData()
            .then((data) => {
                setUser(data);
                setLoading(false);
            })
            .catch((err) => {
                console.error(err);
                setError('⛔ Failed to retrieve user data');
                setLoading(false);
            });
    }, []);

    return (<div style={{padding: '2rem'}}>
        <h2>👤 Профиль</h2>

        {loading && <p>Загрузка...</p>}
        {error && <p style={{color: 'red'}}>{error}</p>}

        {user && (<div
            style={{
                background: '#f8f8f8',
                padding: '1rem',
                borderRadius: '6px',
                display: 'flex',
                gap: '1.5rem',
                alignItems: 'center',
            }}
        >
            <img
                src={user.avatarUrl}
                alt={`${user.username}'s avatar`}
                style={{
                    width: '120px',
                    height: '120px',
                    borderRadius: '50%',
                    objectFit: 'cover',
                    boxShadow: '0 0 6px rgba(0,0,0,0.15)',
                }}
            />
            <div>
                <p><strong>Username:</strong> {user.username}</p>
                <p><strong>Email:</strong> {user.email}</p>
                <p><strong>Registration date:</strong> {user.registeredAt}</p>
            </div>

            <button
                onClick={() => navigate('/settings')}
                style={{
                    background: '#007bff',
                    color: '#fff',
                    border: 'none',
                    padding: '0.6rem 1.2rem',
                    borderRadius: '6px',
                    fontWeight: 'bold',
                    cursor: 'pointer',
                }}
            >
                ⚙️ Update
            </button>
        </div>)}
    </div>);
}
