import {useEffect, useState} from 'react';
import {getUserData, uploadUserAvatar, deleteUser, updateUser, updatePassword} from '../../api/user';
import {useNavigate} from 'react-router-dom';
import {useGlobalLoading} from '../../context/LoadingContext';

export default function Settings() {
    const [user, setUser] = useState(null);
    const [form, setForm] = useState({username: '', email: ''});
    const [preview, setPreview] = useState(null);
    const [status, setStatus] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();
    const {setIsLoading} = useGlobalLoading();
    const [passwordForm, setPasswordForm] = useState({currentPassword: '', newPassword: ''});
    const [passwordStatus, setPasswordStatus] = useState('');

    useEffect(() => {
        document.title = 'Settings';
        setIsLoading(true);
        getUserData()
            .then((data) => {
                setUser(data);
                setForm({username: data.username, email: data.email});
            })
            .catch((err) => {
                console.error(err);
                setError('Failed to load user data');
            })
            .finally(() => {
                setIsLoading(false);
            });
    }, [setIsLoading]);

    const handleFileChange = async (e) => {
        const file = e.target.files[0];
        if (!file) return;

        setPreview(URL.createObjectURL(file));
        setIsLoading(true);

        try {
            const data = await uploadUserAvatar(file);
            setUser((prev) => ({...prev, avatarUrl: data.avatarUrl}));
            setPreview(null);
        } catch (err) {
            console.error(err);
            alert('Failed to upload avatar');
        } finally {
            setIsLoading(false);
        }
    };

    const handleFormChange = (e) => {
        setForm({...form, [e.target.name]: e.target.value});
    };

    const handleFormSubmit = async (e) => {
        e.preventDefault();
        setStatus('Updating...');
        setIsLoading(true);

        try {
            await updateUser(form);
            setStatus('✅ Profile updated');
            setTimeout(() => navigate('/profile'), 500);
        } catch (err) {
            console.error(err);
            setStatus('❌ Failed to update profile');
        } finally {
            setIsLoading(false);
        }
    };

    const handlePasswordFormChange = (e) => {
        setPasswordForm({...passwordForm, [e.target.name]: e.target.value});
    };

    const handlePasswordSubmit = async (e) => {
        e.preventDefault();
        setPasswordStatus('Changing password...');
        setIsLoading(true);

        try {
            await updatePassword(passwordForm);
            setPasswordForm({oldPassword: '', newPassword: ''});
            setPasswordStatus('✅ Password updated');
        } catch (err) {
            console.error(err);
            setPasswordStatus(`❌ ${err.message}`);
        } finally {
            setIsLoading(false);
        }
    };


    const handleDelete = async () => {
        if (!window.confirm('Are you sure you want to delete your account?')) return;
        setIsLoading(true);

        try {
            await deleteUser();
            localStorage.removeItem('token');
            window.location.href = '/';
        } catch (err) {
            console.error(err);
            alert('Failed to delete user');
        } finally {
            setIsLoading(false);
        }
    };

    return (<div style={{padding: '2rem', maxWidth: '600px', margin: 'auto'}}>
        <h2>⚙️ Profile Settings</h2>

        {error && <p style={{color: 'red'}}>{error}</p>}

        {user && (<>
            <div style={{
                display: 'flex', gap: '1.5rem', alignItems: 'center', marginBottom: '2rem'
            }}>
                <div style={{position: 'relative'}}>
                    <img
                        src={preview || user.avatarUrl || '/assets/default-avatar.png'}
                        alt="avatar"
                        style={{
                            width: '120px',
                            height: '120px',
                            borderRadius: '50%',
                            objectFit: 'cover',
                            boxShadow: '0 0 6px rgba(0,0,0,0.15)'
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
                            cursor: 'pointer'
                        }}
                    >
                        Change
                    </label>
                    <input
                        id="avatar-upload"
                        type="file"
                        accept="image/*"
                        onChange={handleFileChange}
                        style={{display: 'none'}}
                    />
                </div>

                <div>
                    <p><strong>Username:</strong> {user.username}</p>
                    <p><strong>Email:</strong> {user.email}</p>
                </div>
            </div>

            <form
                onSubmit={handleFormSubmit}
                style={{
                    display: 'flex', flexDirection: 'column', gap: '1rem'
                }}
            >
                <input
                    type="text"
                    name="username"
                    placeholder="Username"
                    value={form.username}
                    onChange={handleFormChange}
                    required
                />
                <input
                    type="email"
                    name="email"
                    placeholder="Email"
                    value={form.email}
                    onChange={handleFormChange}
                    required
                />
                <button
                    type="submit"
                    style={{
                        background: '#28a745',
                        color: '#fff',
                        padding: '0.6rem',
                        border: 'none',
                        borderRadius: '6px',
                        fontWeight: 'bold',
                        cursor: 'pointer'
                    }}
                >
                    💾 Save changes
                </button>
                {status && <p>{status}</p>}
            </form>

            <hr style={{margin: '2rem 0'}}/>

            <h3>🔐 Change Password</h3>
            <form
                onSubmit={handlePasswordSubmit}
                style={{display: 'flex', flexDirection: 'column', gap: '1rem'}}
            >
                <input
                    type="password"
                    name="currentPassword"
                    placeholder="Current Password"
                    value={passwordForm.currentPassword}
                    onChange={handlePasswordFormChange}
                    required
                />
                <input
                    type="password"
                    name="newPassword"
                    placeholder="New Password"
                    value={passwordForm.newPassword}
                    onChange={handlePasswordFormChange}
                    required
                />
                <button
                    type="submit"
                    style={{
                        background: '#17a2b8',
                        color: '#fff',
                        padding: '0.6rem',
                        border: 'none',
                        borderRadius: '6px',
                        fontWeight: 'bold',
                        cursor: 'pointer'
                    }}
                >
                    🔁 Update Password
                </button>
                {passwordStatus && <p>{passwordStatus}</p>}
            </form>


            <hr style={{margin: '2rem 0'}}/>

            <button
                onClick={handleDelete}
                style={{
                    background: '#dc3545',
                    color: '#fff',
                    border: 'none',
                    padding: '0.6rem 1.2rem',
                    borderRadius: '6px',
                    fontWeight: 'bold',
                    cursor: 'pointer'
                }}
            >
                🗑️ Delete account
            </button>
        </>)}
    </div>);
}
