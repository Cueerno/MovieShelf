import {Link, useNavigate} from 'react-router-dom';
import {useAuth} from '../../context/AuthContext';
import {FaHome, FaSearch, FaUser, FaSignOutAlt, FaUserPlus, FaSignInAlt, FaStar} from 'react-icons/fa';
import './Header.css';

export default function Header() {
    const {token, logout} = useAuth();
    const navigate = useNavigate();

    const handleLogout = () => {
        logout();
        navigate('/login');
    };

    return (
        <header className="header">
            <h2>🎬 MovieShelf</h2>
            <nav className="nav-links">
                <Link to="/" className="nav-link">
                    <span className="nav-link-inner">
                        <FaHome/>
                        <span>Home</span>
                    </span>
                </Link>

                <Link to="/movies" className="nav-link">
                    <span className="nav-link-inner">
                     <FaSearch/>
                         <span>Search</span>
                     </span>
                </Link>

                {token ? (
                    <>
                        <Link to="/profile" className="nav-link">
                            <span className="nav-link-inner">
                                <FaUser/>
                                <span>Profile</span>
                                </span>
                        </Link>

                        <Link to={"/favorites"} className={'nav-link'}>
                            <span className="nav-link-inner">
                                <FaStar/>
                                <span>Favorites</span>
                            </span>
                        </Link>

                        <Link to="/login" className="nav-link" onClick={handleLogout}>
                            <span className="nav-link-inner">
                                <FaSignOutAlt/>
                                <span>Log Out</span>
                            </span>
                        </Link>
                    </>
                ) : (
                    <>
                        <Link to="/signup" className="nav-link">
                            <span className="nav-link-inner">
                                <FaUserPlus/>
                                <span>Sign Up</span>
                            </span>
                        </Link>

                        <Link to="/login" className="nav-link">
                            <span className="nav-link-inner">
                                <FaSignInAlt/>
                                <span>Log In</span>
                            </span>
                        </Link>
                    </>
                )}
            </nav>
        </header>
    );
}
