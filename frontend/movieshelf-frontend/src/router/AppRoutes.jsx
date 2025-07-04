import {Routes, Route} from 'react-router-dom';
import PageLayout from "../layout/PageLayout";
import Home from '../pages/Home/Home';
import MovieSearch from '../pages/Movies/MovieSearch';
import Movie from '../pages/Movie/Movie';
import Profile from "../pages/Profile/Profile";
import Signup from "../pages/Signup";
import Login from "../pages/Login";

export default function AppRoutes() {
    return (
        <Routes>
            <Route path="/" element={<PageLayout/>}>
                <Route path="/" element={<Home/>}/>
                <Route path="/movies" element={<MovieSearch/>}/>
                <Route path="movies/:imdbId" element={<Movie/>}/>

                <Route path="/signup" element={<Signup/>}/>
                <Route path="/login" element={<Login/>}/>
                <Route path="/profile" element={<Profile/>}/>
            </Route>
        </Routes>
    );
}
