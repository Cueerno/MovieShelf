import {useState} from 'react';
import {useNavigate} from "react-router-dom";
import {useAuth} from "../../context/AuthContext";
import {loginUser} from "../../api/auth";
import LoginForm from "../../components/auth/LoginForm";

export default function Login() {
    const [form, setForm] = useState({username: '', password: ''});
    const [status, setStatus] = useState('');
    const navigate = useNavigate();
    const {login} = useAuth();
    const [showPassword, setShowPassword] = useState(false);

    const handleChange = (e) => {
        setForm({...form, [e.target.name]: e.target.value});
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setStatus('⏳ Log In...');

        try {
            const data = await loginUser(form);

            login(data.token);

            setStatus('✅ Success');
            setTimeout(() => navigate('/'), 1000);
        } catch (err) {
            setStatus(err.message);
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

