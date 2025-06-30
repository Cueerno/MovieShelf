import {Link} from 'react-router-dom';

export default function Header() {
    return (
        <header style={{padding: '1rem', backgroundColor: '#1e1e1e', color: '#fff', textAlign: 'center'}}>
            <h1>🎬 MovieShelf</h1>
            <nav style={{marginTop: '0.5rem'}}>
                <Link
                    to="/movies"
                    style={{
                        color: '#fff',
                        textDecoration: 'none',
                        padding: '8px 16px',
                        backgroundColor: '#333',
                        borderRadius: '4px',
                        fontSize: '16px'
                    }}
                >
                    🔍 Movie Search
                </Link>
            </nav>
        </header>
    );
}
