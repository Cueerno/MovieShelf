import {useEffect, useState} from 'react';
import {getUserData, uploadUserAvatar} from '../../api/user';

export default function Profile() {
    const [user, setUser] = useState(null);
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(true);
    const [uploading, setUploading] = useState(false);
    const [preview, setPreview] = useState(null);

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

    const handleFileChange = async (e) => {
        const file = e.target.files[0];
        if (!file) {
            return;
        }

        setPreview(URL.createObjectURL(file));
        setUploading(true);

        try {
            const data = await uploadUserAvatar(file);

            setUser((prev) => ({...prev, avatarUrl: data.avatarUrl}));
            setPreview(null);
        } catch (err) {
            console.error(err);
            alert('⛔ Не удалось загрузить аватар');
        } finally {
            setUploading(false);
        }
    };

    return (
        <div style={{padding: '2rem'}}>
            <h2>🔐 Profile</h2>

            {loading && <p>Loading...</p>}
            {error && <p style={{color: 'red'}}>{error}</p>}

            {user && (
                <div
                    style={{
                        background: '#f8f8f8',
                        padding: '1rem',
                        borderRadius: '6px',
                        display: 'flex',
                        gap: '1.5rem',
                        alignItems: 'flex-start',
                    }}
                >
                    <div style={{position: 'relative'}}>
                        <img
                            src={preview || user.avatarUrl}
                            alt={`${user.username}'s avatar`}
                            style={{
                                width: '120px',
                                height: '120px',
                                borderRadius: '50%',
                                objectFit: 'cover',
                                boxShadow: '0 0 6px rgba(0,0,0,0.15)',
                            }}
                        />
                        <label
                            htmlFor="avatar-upload"
                            style={{
                                position: 'absolute',
                                bottom: 0,
                                right: 0,
                                background: '#007bff',
                                color: '#fff',
                                padding: '4px 8px',
                                borderRadius: '12px',
                                fontSize: '12px',
                                cursor: 'pointer',
                            }}
                        >
                            Изменить
                        </label>
                        <input
                            id="avatar-upload"
                            type="file"
                            accept="image/*"
                            onChange={handleFileChange}
                            style={{display: 'none'}}
                        />
                        {uploading && <p style={{fontSize: '12px'}}>Загрузка...</p>}
                    </div>

                    <div>
                        <p><strong>Username:</strong> {user.username}</p>
                        <p><strong>Email:</strong> {user.email}</p>
                        <p><strong>Registration date:</strong> {user.registeredAt}</p>
                    </div>
                </div>
            )}
        </div>
    );
}
