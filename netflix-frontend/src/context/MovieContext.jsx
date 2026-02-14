import React, { createContext, useState, useContext } from 'react';
import movieService from '../services/movieService';

const MovieContext = createContext();

export const useMovies = () => {
  const context = useContext(MovieContext);
  if (!context) {
    throw new Error('useMovies must be used within MovieProvider');
  }
  return context;
};

export const MovieProvider = ({ children }) => {
  const [movies, setMovies] = useState([]);
  const [featuredMovies, setFeaturedMovies] = useState([]);
  const [trendingMovies, setTrendingMovies] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  const fetchMovies = async (page = 0, size = 20, search = '', genre = '') => {
    setLoading(true);
    setError(null);
    try {
      const data = await movieService.getAllMovies(page, size, search, genre);
      setMovies(data);
      return data;
    } catch (err) {
      setError(err.message);
      throw err;
    } finally {
      setLoading(false);
    }
  };

  const fetchFeaturedMovies = async () => {
    try {
      const data = await movieService.getFeaturedMovies();
      setFeaturedMovies(data);
      return data;
    } catch (err) {
      console.error('Failed to fetch featured movies:', err);
    }
  };

  const fetchTrendingMovies = async () => {
    try {
      const data = await movieService.getTrendingMovies();
      setTrendingMovies(data);
      return data;
    } catch (err) {
      console.error('Failed to fetch trending movies:', err);
    }
  };

  const getMovieById = async (id) => {
    setLoading(true);
    try {
      const movie = await movieService.getMovieById(id);
      return movie;
    } catch (err) {
      setError(err.message);
      throw err;
    } finally {
      setLoading(false);
    }
  };

  const createMovie = async (movieData) => {
    try {
      const movie = await movieService.createMovie(movieData);
      return movie;
    } catch (err) {
      throw err;
    }
  };

  const updateMovie = async (id, movieData) => {
    try {
      const movie = await movieService.updateMovie(id, movieData);
      return movie;
    } catch (err) {
      throw err;
    }
  };

  const deleteMovie = async (id) => {
    try {
      await movieService.deleteMovie(id);
      setMovies(prev => ({
        ...prev,
        content: prev.content?.filter(m => m.id !== id) || []
      }));
    } catch (err) {
      throw err;
    }
  };

  const value = {
    movies,
    featuredMovies,
    trendingMovies,
    loading,
    error,
    fetchMovies,
    fetchFeaturedMovies,
    fetchTrendingMovies,
    getMovieById,
    createMovie,
    updateMovie,
    deleteMovie
  };

  return <MovieContext.Provider value={value}>{children}</MovieContext.Provider>;
};

export default MovieContext;
