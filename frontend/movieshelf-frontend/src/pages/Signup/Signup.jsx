import {useState} from 'react';
import {useNavigate} from "react-router-dom";
import {signup} from "../../api/auth";
import SignupForm from "../../components/auth/SignupForm";

export default function Signup() {
    const [form, setForm] = useState({username: '', email: '', password: ''});
    const [status, setStatus] = useState('');
    const navigate = useNavigate();
    const [showPassword, setShowPassword] = useState(false);

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
            setStatus(err.message);
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
