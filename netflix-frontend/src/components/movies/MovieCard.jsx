import React from 'react';
import { useNavigate } from 'react-router-dom';
import '../../styles/MovieCard.css';

const MovieCard = ({ movie }) => {
  const navigate = useNavigate();

  const handleClick = () => {
    navigate(`/watch/${movie.id}`);
  };

  return (
    <div className="movie-card" onClick={handleClick}>
      <div className="movie-card-image">
        <img src={movie.thumbnailUrl || '/placeholder.jpg'} alt={movie.title} />
        <div className="movie-card-overlay">
          <button className="play-button">▶ Play</button>
          <div className="movie-info">
            <h3>{movie.title}</h3>
            <div className="movie-meta">
              <span className="movie-year">{movie.releaseYear}</span>
              <span className="movie-rating">⭐ {movie.rating?.toFixed(1)}</span>
              <span className="movie-tier">{movie.minimumTier}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default MovieCard;
