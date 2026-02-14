import React from 'react';
import MovieCard from './MovieCard';
import '../../styles/MovieGrid.css';

const MovieGrid = ({ movies, title }) => {
  if (!movies || movies.length === 0) {
    return (
      <div className="movie-grid-container">
        {title && <h2 className="grid-title">{title}</h2>}
        <p className="no-movies">No movies found</p>
      </div>
    );
  }

  return (
    <div className="movie-grid-container">
      {title && <h2 className="grid-title">{title}</h2>}
      <div className="movie-grid">
        {movies.map((movie) => (
          <MovieCard key={movie.id} movie={movie} />
        ))}
      </div>
    </div>
  );
};

export default MovieGrid;
