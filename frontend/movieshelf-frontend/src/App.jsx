import {BrowserRouter} from 'react-router-dom';
import AppRoutes from './router/AppRoutes';
import {LoadingProvider} from "./context/LoadingContext";
import GlobalLoaderOverlay from "./components/layout/GlobalLoaderOverlay";

function App() {
    return (<LoadingProvider>
        <GlobalLoaderOverlay/>
        <BrowserRouter>
            <AppRoutes/>
        </BrowserRouter>
    </LoadingProvider>);
}

export default App;
