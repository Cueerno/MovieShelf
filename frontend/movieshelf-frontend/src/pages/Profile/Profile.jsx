import {useEffect, useState} from 'react';
import {getUserData} from '../../api/user';

export default function Profile() {
    const [user, setUser] = useState(null);
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        getUserData()
            .then((data) => {
                setUser(data);
                setLoading(false);
            })
            .catch((err) => {
                console.error(err);
                setError('⛔ Не удалось получить данные пользователя');
                setLoading(false);
            });
    }, []);


    return (<div style={{padding: '2rem'}}>
        <h2>🔐 Profile</h2>

        {loading && <p>Loading...</p>}

        {error && <p style={{color: 'red'}}>{error}</p>}

        {user && (<div
            style={{
                background: '#f8f8f8',
                padding: '1rem',
                borderRadius: '6px',
                display: 'flex',
                gap: '1.5rem',
                alignItems: 'flex-start',
            }}
        >
            {/*<img*/}
            {/*    src={`http://localhost:8080${user.avatarUrl}`}*/}
            {/*    alt={`${user.username}'s avatar`}*/}
            {/*    style={{*/}
            {/*        width: '120px',*/}
            {/*        height: '120px',*/}
            {/*        borderRadius: '50%',*/}
            {/*        objectFit: 'cover',*/}
            {/*        boxShadow: '0 0 6px rgba(0,0,0,0.15)',*/}
            {/*    }}*/}
            {/*/>*/}
            <div>
                <p><strong>Username:</strong> {user.username}</p>
                <p><strong>Email:</strong> {user.email}</p>
                <p><strong>Registration date:</strong> {user.registeredAt}</p>
            </div>
        </div>)}
    </div>);
}
