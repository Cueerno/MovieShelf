import {useState, useEffect} from 'react';
import {useNavigate} from 'react-router-dom';
import {useAuth} from '../../context/AuthContext';
import {loginUser} from '../../api/auth';
import LoginForm from '../../components/auth/LoginForm';
import {useGlobalLoading} from '../../context/LoadingContext';

export default function Login() {
    const [form, setForm] = useState({username: '', password: ''});
    const [status, setStatus] = useState('');
    const [showPassword, setShowPassword] = useState(false);
    const navigate = useNavigate();
    const {login} = useAuth();
    const {setIsLoading} = useGlobalLoading();

    useEffect(() => {
        document.title = 'Login';
    }, []);

    const handleChange = (e) => {
        setForm({...form, [e.target.name]: e.target.value});
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setIsLoading(true);

        try {
            const data = await loginUser(form);
            login(data.token);
            setStatus('✅ Success');
            setTimeout(() => navigate('/'), 1000);
        } catch (err) {
            console.error(err);
            setStatus(`❌ ${err.message}`);
        } finally {
            setIsLoading(false);
        }
    };

    return (<div style={{padding: '2rem', maxWidth: '400px', margin: 'auto'}}>
        <LoginForm
            form={form}
            handleChange={handleChange}
            handleSubmit={handleSubmit}
            status={status}
            showPassword={showPassword}
            setShowPassword={setShowPassword}
        />
    </div>);
}

