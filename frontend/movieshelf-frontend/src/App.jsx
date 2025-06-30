import { BrowserRouter } from 'react-router-dom';
import AppRoutes from './router/AppRoutes';

function App() {
    return (
        <BrowserRouter>
            <div style={{ padding: '20px', fontFamily: 'sans-serif' }}>
                <h1 style={{ textAlign: 'center' }}>🎬 MovieShelf</h1>
                <AppRoutes />
            </div>
        </BrowserRouter>
    );
}

export default App;
