import {useParams} from 'react-router-dom';
import {useEffect, useState} from 'react';
import {movieByImdbId} from '../../api/movie';
import {addToFavorites, deleteFromFavorites} from '../../api/favorites';
import {addReaction, deleteReaction} from '../../api/reaction';
import {FaStar} from 'react-icons/fa';
import {useGlobalLoading} from '../../context/LoadingContext';
import CommentSection from '../../components/comment/CommentSection';
import {addRating, deleteRating} from "../../api/rating";

export default function Movie() {
    const {imdbId} = useParams();
    const [movie, setMovie] = useState(null);
    const [error, setError] = useState('');
    const [favError, setFavError] = useState('');
    const {setIsLoading} = useGlobalLoading();
    const [showComments, setShowComments] = useState(false);
    const [commentsCount, setCommentsCount] = useState(0);

    const [likeCount, setLikeCount] = useState(0);
    const [dislikeCount, setDislikeCount] = useState(0);
    const [userReaction, setUserReaction] = useState(null);
    const [reactionError, setReactionError] = useState('');

    const [ratingValue, setRatingValue] = useState('');
    const [userRating, setUserRating] = useState(null);
    const [ratingError, setRatingError] = useState('');
    const [averageRating, setAverageRating] = useState(0);

    useEffect(() => {
        setIsLoading(true);
        movieByImdbId(imdbId)
            .then(data => {
                setMovie(data);
                setCommentsCount(data.addtionalMovieInformation.commentsCount);
                setLikeCount(data.addtionalMovieInformation.likesCount);
                setDislikeCount(data.addtionalMovieInformation.dislikesCount);
                setAverageRating(data.addtionalMovieInformation.averageRating);

                setUserReaction(data.addtionalMovieInformation.userReactionType || null);

                if(data.addtionalMovieInformation.score != null) {
                    setUserRating(data.addtionalMovieInformation.score);
                    setRatingValue(data.addtionalMovieInformation.score)
                }
            })
            .catch(err => setError(err.message))
            .finally(() => setIsLoading(false));
    }, [imdbId]);

    useEffect(() => {
        if (movie) {
            document.title = movie.title;
        }
    }, [movie]);

    if (error) return <p style={{color: 'red'}}>❌ {error}</p>;
    if (!movie) return null;

    const handleLike = async () => {
        try {
            if (userReaction === 'LIKE') {
                await deleteReaction(imdbId);
                setLikeCount(prev => prev - 1);
                setUserReaction(null);
                setMovie(prev => ({
                    ...prev,
                    userReactionType: null,
                    likesCount: prev.likesCount - 1
                }));
                return;
            }

            if (userReaction === 'DISLIKE') {
                await deleteReaction(imdbId);
                setDislikeCount(prev => prev - 1);
            }

            await addReaction(imdbId, { reactionType: 'LIKE' });
            setLikeCount(prev => prev + 1);
            setUserReaction('LIKE');

            setMovie(prev => ({
                ...prev,
                userReactionType: 'LIKE',
                likesCount: prev.likesCount + 1,
                dislikesCount: userReaction === 'DISLIKE' ? prev.dislikesCount - 1 : prev.dislikesCount,
            }));
        } catch (err) {
            setReactionError(err.message);
        }
    };

    const handleDislike = async () => {
        try {
            if (userReaction === 'DISLIKE') {
                await deleteReaction(imdbId);
                setDislikeCount(prev => prev - 1);
                setUserReaction(null);
                setMovie(prev => ({
                    ...prev,
                    userReactionType: null,
                    dislikesCount: prev.dislikesCount - 1
                }));
                return;
            }

            if (userReaction === 'LIKE') {
                await deleteReaction(imdbId);
                setLikeCount(prev => prev - 1);
            }

            await addReaction(imdbId, { reactionType: 'DISLIKE' });
            setDislikeCount(prev => prev + 1);
            setUserReaction('DISLIKE');

            setMovie(prev => ({
                ...prev,
                userReactionType: 'DISLIKE',
                dislikesCount: prev.dislikesCount + 1,
                likesCount: userReaction === 'LIKE' ? prev.likesCount - 1 : prev.likesCount,
            }));
        } catch (err) {
            setReactionError(err.message);
        }
    };

    const handleToggleFavorite = () => {
        const action = movie.extraMovieInformation.isUserFavorite ? deleteFromFavorites : addToFavorites;

        action(imdbId)
            .then(() => {
                setMovie(prev => ({
                    ...prev,
                    isUserFavorite: !prev.isUserFavorite,
                    favoriteCount: prev.isUserFavorite
                    ? prev.favoriteCount - 1
                    : prev.favoriteCount + 1
                }));
            })
            .catch(err => setFavError(err.message));
    };

    const handleRatingSubmit = async () => {
        try {
            const updated = await addRating(imdbId, { score: Number(ratingValue) });
            setAverageRating(updated.averageRating);
            setUserRating(updated.userRating);
            setRatingError('');
        } catch (err) {
            setRatingError(err.message);
        }
    };

    const handleRatingDelete = async () => {
        try {
            const updated = await deleteRating(imdbId);
            setAverageRating(updated.averageRating);
            setUserRating(null);
            setRatingValue('');
            setRatingError('');
        } catch (err) {
            setRatingError(err.message);
        }
    };

    return (<div style={{padding: '2rem', display: 'flex', gap: '2rem', alignItems: 'flex-start'}}>
        <div style={{display: 'flex', flexDirection: 'column', alignItems: 'center'}}>
            <img
                src={movie.poster}
                alt={movie.title}
                style={{
                    width: '220px',
                    height: '320px',
                    objectFit: 'cover',
                    borderRadius: '8px',
                    boxShadow: '0 0 10px rgba(0,0,0,0.2)',
                }}
                onError={e => (e.target.src = '/assets/default-movie.png')}
            />

            <h3 style={{ marginTop: '1rem' }}>⭐ Favorited by: {movie.addtionalMovieInformation.favoriteCount}</h3>

            <button
                onClick={handleToggleFavorite}
                style={{
                    backgroundColor: movie.addtionalMovieInformation.isUserFavorite ? '#e50914' : '#555',
                    color: 'white',
                    padding: '0.6rem 1.2rem',
                    border: 'none',
                    borderRadius: '4px',
                    cursor: 'pointer',
                    display: 'flex',
                    alignItems: 'center',
                    gap: '0.5rem',
                    fontWeight: 'bold',
                    marginTop: '1rem',
                }}
            >
                <FaStar/>
                {movie.addtionalMovieInformation.isUserFavorite ? 'Remove from favorites' : 'Add to favorites'}
            </button>
            {favError && <p style={{color: 'red', marginTop: '0.5rem'}}>❌ {favError}</p>}

            <div style={{ marginTop: '1.5rem', textAlign: 'center' }}>
                <h3>Average: '{averageRating}'</h3>
                <label htmlFor="ratingInput">Rate (1–100): </label>
                <input
                    id="ratingInput"
                    type="number"
                    min="1"
                    max="100"
                    value={ratingValue}
                    onChange={e => setRatingValue(e.target.value)}
                    style={{ width: '60px', marginLeft: '0.5rem' }}
                />
                <div style={{ marginTop: '0.5rem', display: 'flex', gap: '0.5rem', justifyContent: 'center' }}>
                    <button
                        onClick={handleRatingSubmit}
                        disabled={!ratingValue}
                        style={{
                            padding: '0.4rem 0.8rem',
                            cursor: 'pointer',
                            backgroundColor: '#007bff',
                            color: 'white',
                            border: 'none',
                            borderRadius: '4px'
                        }}
                    >
                        {userRating != null ? 'Update Rating' : 'Submit Rating'}
                    </button>

                    {userRating != null && (
                        <button
                            onClick={handleRatingDelete}
                            style={{
                                padding: '0.4rem 0.8rem',
                                cursor: 'pointer',
                                backgroundColor: '#ccc',
                                border: 'none',
                                borderRadius: '4px'
                            }}
                        >
                            Remove Rating
                        </button>
                    )}
                </div>
                {ratingError && <p style={{ color: 'red', marginTop: '0.5rem' }}>❌ {ratingError}</p>}
            </div>
        </div>

        <div>
            <h2 style={{marginBottom: '0.5rem'}}>{movie.title}</h2>
            <p><strong>Year: </strong>{movie.year}</p>
            <p><strong>Type: </strong>{movie.type?.charAt(0).toUpperCase() + movie.type?.slice(1)}</p>
            <p><strong>Rated: </strong>{movie.rated}</p>
            <p><strong>Released: </strong>{movie.released}</p>
            <p><strong>Runtime: </strong>{movie.runtime}</p>
            <p><strong>Genre: </strong>{movie.genre}</p>
            <p><strong>Director: </strong>{movie.director}</p>
            <p><strong>Writer: </strong>{movie.writer}</p>
            <p><strong>Actors: </strong>{movie.actors}</p>
            <p><strong>Plot: </strong>{movie.plot}</p>
            <p><strong>Language: </strong>{movie.language}</p>
            <p><strong>Country: </strong>{movie.country}</p>
            <p><strong>Awards: </strong>{movie.awards}</p>
            <p><strong>Metascore: </strong>{movie.metascore}</p>
            <p><strong>IMDBRating: </strong>{movie.imdbRating}</p>
            <p><strong>IMDBVotes: </strong>{movie.imdbVotes}</p>
            <p><strong>DVD: </strong>{movie.dvd}</p>
            <p><strong>BoxOffice: </strong>{movie.boxOffice}</p>
            <p><strong>Production: </strong>{movie.production}</p>
            <p><strong>Website: </strong>{movie.website}</p>
            <div style={{marginTop: '1rem'}}>
                <h3>Ratings</h3>
                <ul style={{paddingLeft: '1.2rem'}}>
                    {movie.ratings?.map((rating, idx) => (<li key={idx}>
                        <strong>{rating.source}:</strong> {rating.value}
                    </li>))}
                </ul>
            </div>

            <div style={{display: 'flex', alignItems: 'center', gap: '1rem', marginTop: '1rem'}}>
                <button
                    onClick={handleLike}
                    style={{
                        background: 'none',
                        border: 'none',
                        cursor: 'pointer',
                        color: userReaction === 'LIKE' ? '#007bff' : '#666',
                        display: 'flex',
                        alignItems: 'center',
                        gap: '0.25rem',
                        fontSize: '1.1rem',
                    }}
                >
                    👍 {likeCount}
                </button>

                <button
                    onClick={handleDislike}
                    style={{
                        background: 'none',
                        border: 'none',
                        cursor: 'pointer',
                        color: userReaction === 'DISLIKE' ? '#007bff' : '#666',
                        display: 'flex',
                        alignItems: 'center',
                        gap: '0.25rem',
                        fontSize: '1.1rem',
                    }}
                >
                    👎 {dislikeCount}
                </button>

                {reactionError && <p style={{color: 'red'}}>❌ {reactionError}</p>}
            </div>

            <button
                onClick={() => setShowComments(prev => !prev)}
                style={{
                    backgroundColor: '#eee',
                    border: 'none',
                    padding: '0.5rem 1rem',
                    borderRadius: '20px',
                    cursor: 'pointer',
                    fontWeight: '500',
                    marginTop: '1rem',
                }}
            >
                💬 Comments {commentsCount > 0 && `(${commentsCount})`}
            </button>

            {showComments && (<div style={{marginTop: '1.5rem'}}>
                <CommentSection imdbId={imdbId} setCommentsCount={setCommentsCount}/>
            </div>)}
        </div>
    </div>);
}
