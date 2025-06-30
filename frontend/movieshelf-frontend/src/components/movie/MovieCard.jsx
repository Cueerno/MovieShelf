import React from 'react';

function MovieCard({ movie }) {
    return (
        <div
            style={{
                width: '160px',
                background: '#fff',
                padding: '10px',
                boxShadow: '0 0 5px rgba(0,0,0,0.2)',
                display: 'flex',
                flexDirection: 'column',
                alignItems: 'center',
                borderRadius: '6px',
                textAlign: 'center'
            }}
        >
            <img
                src={movie.poster}
                alt={movie.title}
                style={{
                    width: '150px',
                    height: '220px',
                    objectFit: 'cover',
                    borderRadius: '4px'
                }}
                onError={(e) =>
                    (e.target.src = 'https://via.placeholder.com/150x220?text=No+Poster')
                }
            />
            <h4 style={{ margin: '10px 0 5px' }}>{movie.title}</h4>
            <p style={{ margin: '4px 0', color: '#999', fontSize: '16px' }}>
                {movie.type?.charAt(0).toUpperCase() + movie.type?.slice(1)}
            </p>
            <p style={{ margin: 0, color: '#666', fontSize: '14px' }}>
                {movie.year}
            </p>
        </div>
    );
}

export default MovieCard;
