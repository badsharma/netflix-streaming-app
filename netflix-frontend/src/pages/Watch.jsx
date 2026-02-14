import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { useMovies } from '../context/MovieContext';
import { useSubscription } from '../context/SubscriptionContext';
import VideoPlayer from '../components/video/VideoPlayer';
import watchHistoryService from '../services/watchHistoryService';
import Loader from '../components/common/Loader';
import '../styles/Watch.css';

const Watch = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const { getMovieById } = useMovies();
  const { subscription } = useSubscription();

  const [movie, setMovie] = useState(null);
  const [streamUrl, setStreamUrl] = useState(null);
  const [quality, setQuality] = useState('SD');
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    loadMovie();
  }, [id]);

  const loadMovie = async () => {
    setLoading(true);
    setError('');

    try {
      const movieData = await getMovieById(id);
      setMovie(movieData);

      // Validate access and get stream URL
      const validation = await watchHistoryService.validateStreamingAccess(id, quality);

      if (!validation.canWatch) {
        setError(validation.reason);
        setLoading(false);
        return;
      }

      const streamData = await watchHistoryService.getStreamUrl(id, quality);
      setStreamUrl(streamData.streamUrl);
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to load movie');
    } finally {
      setLoading(false);
    }
  };

  const handleQualityChange = async (newQuality) => {
    try {
      const streamData = await watchHistoryService.getStreamUrl(id, newQuality);
      setStreamUrl(streamData.streamUrl);
      setQuality(newQuality);
    } catch (err) {
      setError('Failed to change quality: ' + err.message);
    }
  };

  if (loading) {
    return <Loader />;
  }

  if (error) {
    return (
      <div className="watch-error">
        <h2>Unable to play video</h2>
        <p>{error}</p>
        <button onClick={() => navigate('/subscription')} className="upgrade-button">
          Upgrade Subscription
        </button>
        <button onClick={() => navigate(-1)} className="back-button">
          Go Back
        </button>
      </div>
    );
  }

  return (
    <div className="watch-page">
      {streamUrl && movie && (
        <>
          <VideoPlayer movie={movie} videoUrl={streamUrl} quality={quality} />

          <div className="movie-details">
            <h1>{movie.title}</h1>
            <div className="movie-meta">
              <span>{movie.releaseYear}</span>
              <span>⭐ {movie.rating?.toFixed(1)}</span>
              <span>{movie.durationMinutes} min</span>
              <span className="tier-badge">{movie.minimumTier}</span>
            </div>

            <div className="quality-selector">
              <label>Quality: </label>
              {subscription?.allowedQualities.map((q) => (
                <button
                  key={q}
                  onClick={() => handleQualityChange(q)}
                  className={`quality-button ${quality === q ? 'active' : ''}`}
                  disabled={!subscription.allowedQualities.includes(q)}
                >
                  {q}
                </button>
              ))}
            </div>

            <p className="movie-description">{movie.description}</p>

            <div className="movie-genres">
              {movie.genres?.map((genre) => (
                <span key={genre} className="genre-tag">{genre}</span>
              ))}
            </div>
          </div>
        </>
      )}
    </div>
  );
};

export default Watch;
