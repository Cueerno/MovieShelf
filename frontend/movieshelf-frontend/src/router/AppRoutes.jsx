import {Routes, Route} from 'react-router-dom';
import PageLayout from "../layout/PageLayout";
import Home from '../pages/Home/Home';
import MovieSearch from '../pages/Movies/MovieSearch';
import MovieDetail from '../pages/MovieDetail/MovieDetail';

export default function AppRoutes() {
    return (
        <Routes>
            <Route path="/" element={<PageLayout/>}>
                <Route path="/" element={<Home />} />
                <Route path="/movies" element={<MovieSearch/>}/>
                <Route path="/movies/:movieId" element={<MovieDetail/>}/>
            </Route>
        </Routes>
    );
}
