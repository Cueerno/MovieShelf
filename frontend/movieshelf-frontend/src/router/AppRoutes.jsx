import { Routes, Route } from 'react-router-dom';
//import Home from '../pages/Home/Home';
import MovieSearch from '../pages/Movies/MovieSearch';
//import MovieDetail from '../pages/MovieDetail/MovieDetail';
//import UserProfile from '../pages/Profile/UserProfile';
//import Favorites from '../pages/Favorites/Favorites';

export default function AppRoutes() {
    return (
        <Routes>
            {/*<Route path="/" element={<Home />} />*/}
            <Route path="/movies" element={<MovieSearch />} />
            {/*<Route path="/movies/:movieId" element={<MovieDetail />} />*/}
            {/*<Route path="/users/:username" element={<UserProfile />} />*/}
            {/*<Route path="/users/:username/favorites" element={<Favorites />} />*/}
        </Routes>
    );
}
