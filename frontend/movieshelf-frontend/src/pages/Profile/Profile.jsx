import {useEffect, useState} from 'react';
import {getUserData} from '../../api/user';
import {useNavigate} from 'react-router-dom';
import {useGlobalLoading} from '../../context/LoadingContext';

export default function Profile() {
    const [user, setUser] = useState(null);
    const [error, setError] = useState('');
    const navigate = useNavigate();
    const {setIsLoading} = useGlobalLoading();

    useEffect(() => {
        setIsLoading(true);
        getUserData()
            .then((data) => {
                setUser(data);
            })
            .catch((err) => {
                console.error(err);
                setError('⛔ Failed to retrieve user data');
            })
            .finally(() => {
                setIsLoading(false);
            });
    }, [setIsLoading]);

    const formatDate = (isoString) => {
        if (!isoString) return '';
        const date = new Date(isoString);
        return date.toLocaleDateString('en-EN', {
            day: 'numeric', month: 'long', year: 'numeric', hour: '2-digit', minute: '2-digit',
        });
    };

    return (<div style={{padding: '2rem'}}>
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
                src={user.avatarUrl || '/assets/default-avatar.png'}
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
                <p><strong>Registration date:</strong> {formatDate(user.registeredAt)}</p>
                <p><strong>Last Log In:</strong> {formatDate(user.lastLoginAt)}</p>
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

