import React from 'react';
import {Link} from 'react-router-dom';

function MovieCard({movie}) {
    return (
        <Link to={`/movies/${movie.imdbId}`} style={{textDecoration: 'none', color: 'inherit'}}>
            <div
                style={{
                    width: '160px',
                    height: '340px',
                    background: '#fff',
                    padding: '10px',
                    boxShadow: '0 0 5px rgba(0,0,0,0.2)',
                    display: 'flex',
                    flexDirection: 'column',
                    alignItems: 'center',
                    borderRadius: '6px',
                    textAlign: 'center',
                    transition: 'transform 0.2s',
                }}
                onMouseOver={(e) => (e.currentTarget.style.transform = 'scale(1.03)')}
                onMouseOut={(e) => (e.currentTarget.style.transform = 'scale(1.00)')}
            >
                <img
                    src={movie.poster}
                    alt={movie.title}
                    style={{
                        width: '150px',
                        height: '220px',
                        objectFit: 'cover',
                        borderRadius: '4px',
                    }}
                    onError={(e) =>
                        (e.target.src = 'https://via.placeholder.com/150x220?text=No+Poster')
                    }
                />
                <h4 title={movie.title}
                    style={{
                        margin: '10px 0 5px',
                        maxHeight: '40px',
                        overflow: 'hidden',
                        textOverflow: 'ellipsis',
                        display: '-webkit-box',
                        WebkitLineClamp: 2,
                        WebkitBoxOrient: 'vertical',
                    }}
                >
                    {movie.title}
                </h4>
                <p style={{margin: '4px 0', color: '#999', fontSize: '16px'}}>
                    {movie.type?.charAt(0).toUpperCase() + movie.type?.slice(1)}
                </p>
                <p style={{margin: 0, color: '#666', fontSize: '14px'}}>
                    {movie.year}
                </p>
            </div>
        </Link>
    );
}

export default MovieCard;

