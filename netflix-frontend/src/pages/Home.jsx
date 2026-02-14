import React, { useEffect, useState } from 'react';
import { useMovies } from '../context/MovieContext';
import watchHistoryService from '../services/watchHistoryService';
import MovieGrid from '../components/movies/MovieGrid';
import Loader from '../components/common/Loader';
import '../styles/Home.css';

const Home = () => {
  const { featuredMovies, trendingMovies, fetchFeaturedMovies, fetchTrendingMovies } = useMovies();
  const [continueWatching, setContinueWatching] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadHomeData();
  }, []);

  const loadHomeData = async () => {
    setLoading(true);
    try {
      await Promise.all([
        fetchFeaturedMovies(),
        fetchTrendingMovies(),
        loadContinueWatching()
      ]);
    } catch (error) {
      console.error('Failed to load home data:', error);
    } finally {
      setLoading(false);
    }
  };

  const loadContinueWatching = async () => {
    try {
      const data = await watchHistoryService.getContinueWatching();
      setContinueWatching(data);
    } catch (error) {
      console.log('No continue watching data');
    }
  };

  if (loading) {
    return <Loader />;
  }

  return (
    <div className="home-page">
      <div className="hero-section">
        <div className="hero-content">
          <h1>Unlimited movies and TV shows</h1>
          <p>Watch anywhere. Cancel anytime.</p>
        </div>
      </div>

      {continueWatching.length > 0 && (
        <section className="content-section">
          <h2>Continue Watching</h2>
          <div className="movie-row">
            {continueWatching.map((history) => (
              <div key={history.id} className="continue-watching-item">
                {/* You would fetch and display movie details here */}
                <p>Movie ID: {history.movieId}</p>
                <div className="progress-bar">
                  <div
                    className="progress-fill"
                    style={{
                      width: `${(history.progressSeconds / history.totalDurationSeconds) * 100}%`
                    }}
                  />
                </div>
              </div>
            ))}
          </div>
        </section>
      )}

      <section className="content-section">
        <MovieGrid movies={featuredMovies} title="Featured Movies" />
      </section>

      <section className="content-section">
        <MovieGrid movies={trendingMovies} title="Trending Now" />
      </section>
    </div>
  );
};

export default Home;
