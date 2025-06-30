import Header from '../components/Header';
import Footer from '../components/Footer';
import { Outlet } from 'react-router-dom';

export default function PageLayout() {
    return (
        <div style={{ minHeight: '100vh', display: 'flex', flexDirection: 'column' }}>
            <Header />
            <main style={{ flexGrow: 1, padding: '2rem' }}>
                <Outlet />
            </main>
            <Footer />
        </div>
    );
}