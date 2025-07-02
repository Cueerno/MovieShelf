import {Link} from 'react-router-dom';
import {FaHome, FaSearch} from 'react-icons/fa';
import './Header.css';

export default function Header() {
    return (
        <header className="header">
            <h2>🎬 MovieShelf</h2>
            <nav className="nav-links">
                <Link to="/" className="nav-link">
                    <span className="nav-link-inner">
                        <FaHome />
                        <span>Home</span>
                    </span>
                </Link>
                <Link to="/movies" className="nav-link">
                    <span className="nav-link-inner">
                        <FaSearch/>
                        <span>Search</span>
                    </span>
                </Link>
            </nav>
        </header>
    );
}
