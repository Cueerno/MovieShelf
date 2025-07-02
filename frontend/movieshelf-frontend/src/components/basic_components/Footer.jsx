import { FaEnvelope, FaTelegramPlane, FaGithub } from 'react-icons/fa';
import './Footer.css';

export default function Footer() {
    return (
        <footer className="footer">
            <p>© {new Date().getFullYear()} MovieShelf by Vyacheslav Radiuk</p>
            <div className="footer-icons">
                <a href="mailto:vyacheslavradiuk@gmail.com" title="Email">
                    <FaEnvelope />
                </a>
                <a href="https://t.me/vyacheslavradiuk" target="_blank" rel="noopener noreferrer" title="Telegram">
                    <FaTelegramPlane />
                </a>
                <a href="https://github.com/Cueerno" target="_blank" rel="noopener noreferrer" title="GitHub">
                    <FaGithub />
                </a>
            </div>
        </footer>
    );
}
