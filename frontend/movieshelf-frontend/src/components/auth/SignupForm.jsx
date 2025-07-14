import React from 'react';
import './FormStyles.css';

function SignUpForm({ form, handleChange, handleSubmit, status, showPassword, setShowPassword }) {
    const EyeOpen = (
        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor" width="24" height="24">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z" />
        </svg>
    );

    const EyeClosed = (
        <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" stroke="currentColor" width="24" height="24">
            <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M3 3l18 18M10.477 10.477A3 3 0 0012 15a3 3 0 002.121-5.121m3.536 3.535A9.977 9.977 0 0112 19c-3.866 0-7.245-2.207-8.879-5.5a10.052 10.052 0 012.118-3.14m3.062-1.63A9.977 9.977 0 0112 5c3.866 0 7.245 2.207 8.879 5.5a9.962 9.962 0 01-1.51 2.295" />
        </svg>
    );

    return (
        <div className="form-box">
            <form className="form" onSubmit={handleSubmit}>
                <span className="title">Sign up</span>
                <div className="form-container">
                    <input
                        type="text"
                        name="username"
                        className="input"
                        placeholder="Username"
                        value={form.username}
                        onChange={handleChange}
                        required
                    />
                    <input
                        type="email"
                        name="email"
                        className="input"
                        placeholder="Email"
                        value={form.email}
                        onChange={handleChange}
                        required
                    />
                    <div style={{ position: 'relative' }}>
                        <input
                            type={showPassword ? 'text' : 'password'}
                            name="password"
                            className="input"
                            placeholder="Password"
                            value={form.password}
                            onChange={handleChange}
                            required
                            style={{ paddingRight: '40px' }}
                        />
                        <button
                            type="button"
                            onClick={() => setShowPassword((prev) => !prev)}
                            style={{
                                position: 'absolute',
                                right: '10px',
                                top: '50%',
                                transform: 'translateY(-50%)',
                                background: 'none',
                                border: 'none',
                                cursor: 'pointer',
                                fontSize: '1rem',
                                color: '#888',
                            }}
                        >
                            {showPassword ? EyeClosed : EyeOpen}
                        </button>
                    </div>

                </div>
                <button type="submit">Sign up</button>
                <p>{status}</p>
            </form>
            <div className="form-section">
                <p>
                    Have an account? <a href="/login">Log in</a>
                </p>
            </div>
        </div>
    );
}

export default SignUpForm;