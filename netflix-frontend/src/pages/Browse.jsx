import React, { useEffect, useState } from 'react';
import { useMovies } from '../context/MovieContext';
import SearchBar from '../components/movies/SearchBar';
import MovieGrid from '../components/movies/MovieGrid';
import Loader from '../components/common/Loader';
import '../styles/Browse.css';

const Browse = () => {
  const { movies, fetchMovies, loading } = useMovies();
  const [searchTerm, setSearchTerm] = useState('');
  const [selectedGenre, setSelectedGenre] = useState('');
  const [page, setPage] = useState(0);

  useEffect(() => {
    loadMovies();
  }, [searchTerm, selectedGenre, page]);

  const loadMovies = async () => {
    try {
      await fetchMovies(page, 20, searchTerm, selectedGenre);
    } catch (error) {
      console.error('Failed to load movies:', error);
    }
  };

  const handleSearch = (term) => {
    setSearchTerm(term);
    setPage(0);
  };

  const handleGenreChange = (genre) => {
    setSelectedGenre(genre);
    setPage(0);
  };

  const handleLoadMore = () => {
    setPage(prev => prev + 1);
  };

  return (
    <div className="browse-page">
      <div className="browse-header">
        <h1>Browse Movies</h1>
        <SearchBar onSearch={handleSearch} onGenreChange={handleGenreChange} />
      </div>

      {loading && page === 0 ? (
        <Loader />
      ) : (
        <>
          <MovieGrid movies={movies.content || []} />

          {movies.content && !movies.last && (
            <div className="load-more-container">
              <button onClick={handleLoadMore} className="load-more-button" disabled={loading}>
                {loading ? 'Loading...' : 'Load More'}
              </button>
            </div>
          )}

          {movies.content && movies.content.length === 0 && (
            <div className="no-results">
              <h2>No movies found</h2>
              <p>Try adjusting your search or filter</p>
            </div>
          )}
        </>
      )}
    </div>
  );
};

export default Browse;
