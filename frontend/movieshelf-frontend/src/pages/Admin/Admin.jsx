import {useEffect, useState} from 'react';
import {getAllUsers} from '../../api/admin';
import {useGlobalLoading} from "../../context/LoadingContext";
import './Admin.css';

export default function AdminPage() {
    const [users, setUsers] = useState([]);
    const [error, setError] = useState('');
    const {setIsLoading} = useGlobalLoading();

    useEffect(() => {
        document.title = 'Admin';
        setIsLoading(true);
        getAllUsers()
            .then(data => {
                setUsers(data);
                setIsLoading(false);
            })
            .catch(err => {
                setError(err.message);
                setIsLoading(false);
            });
    }, []);

    return (<div className="admin-container">
        <h2>👑 Admin Panel</h2>

        {error && <p className="status error">❌ {error}</p>}

        {!error && (<table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Avatar</th>
                <th>Username</th>
                <th>Email</th>
                <th>Role</th>
                <th>Registered At</th>
                <th>Last Login</th>
                <th>Updated At</th>
            </tr>
            </thead>
            <tbody>
            {users.map(user => (<tr key={user.id}>
                <td>{user.id}</td>
                <td>
                    <img className={'user-avatar'}
                         src={user.avatarUrl || "/assets/default-avatar.png"}
                         alt="avatar"
                         width="50"
                         height="50"
                    />
                </td>
                <td>{user.username}</td>
                <td>{user.email}</td>
                <td>{user.role.replace('ROLE_', '')}</td>
                <td>{new Date(user.registeredAt).toLocaleString()}</td>
                <td>{new Date(user.lastLoginAt).toLocaleString()}</td>
                <td>{new Date(user.updatedAt).toLocaleString()}</td>
            </tr>))}
            </tbody>
        </table>)}
    </div>);
}
