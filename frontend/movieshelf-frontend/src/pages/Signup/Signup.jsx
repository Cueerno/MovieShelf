import {useEffect, useState} from 'react';
import {useNavigate} from 'react-router-dom';
import {signup} from '../../api/auth';
import SignupForm from '../../components/auth/SignupForm';
import {useGlobalLoading} from '../../context/LoadingContext';

export default function Signup() {
    const [form, setForm] = useState({
        username: '', email: '', password: ''
    });
    const [status, setStatus] = useState('');
    const [showPassword, setShowPassword] = useState(false);
    const navigate = useNavigate();
    const {setIsLoading} = useGlobalLoading();

    useEffect(() => {
        document.title = 'Signup';
    }, []);

    const handleChange = (e) => {
        setForm({...form, [e.target.name]: e.target.value});
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setIsLoading(true);

        try {
            await signup(form);
            setForm({username: '', email: '', password: ''});
            setStatus('✅ Registration successful');
            setTimeout(() => navigate('/login'), 1000);
        } catch (err) {
            console.error(err);
            setStatus(`❌ ${err.message}`);
        } finally {
            setIsLoading(false);
        }
    };

    return (<div style={{padding: '2rem', maxWidth: '400px', margin: 'auto'}}>
        <SignupForm
            form={form}
            handleChange={handleChange}
            handleSubmit={handleSubmit}
            status={status}
            showPassword={showPassword}
            setShowPassword={setShowPassword}
        />
    </div>);
}
