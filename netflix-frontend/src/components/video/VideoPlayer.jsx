import React, { useRef, useState, useEffect } from 'react';
import watchHistoryService from '../../services/watchHistoryService';
import '../../styles/VideoPlayer.css';

const VideoPlayer = ({ movie, videoUrl, quality }) => {
  const videoRef = useRef(null);
  const [isPlaying, setIsPlaying] = useState(false);
  const [currentTime, setCurrentTime] = useState(0);
  const [duration, setDuration] = useState(0);
  const [volume, setVolume] = useState(1);
  const [isFullscreen, setIsFullscreen] = useState(false);

  useEffect(() => {
    loadWatchProgress();
    const interval = setInterval(saveProgress, 10000); // Save every 10 seconds
    return () => clearInterval(interval);
  }, [movie.id]);

  const loadWatchProgress = async () => {
    try {
      const progress = await watchHistoryService.getWatchProgress(movie.id);
      if (progress && videoRef.current) {
        videoRef.current.currentTime = progress.progressSeconds;
      }
    } catch (error) {
      console.log('No previous progress found');
    }
  };

  const saveProgress = async () => {
    if (videoRef.current && isPlaying) {
      try {
        await watchHistoryService.saveWatchProgress(
          movie.id,
          Math.floor(videoRef.current.currentTime),
          Math.floor(videoRef.current.duration),
          quality
        );
      } catch (error) {
        console.error('Failed to save progress:', error);
      }
    }
  };

  const togglePlay = () => {
    if (videoRef.current) {
      if (isPlaying) {
        videoRef.current.pause();
      } else {
        videoRef.current.play();
      }
      setIsPlaying(!isPlaying);
    }
  };

  const handleTimeUpdate = () => {
    if (videoRef.current) {
      setCurrentTime(videoRef.current.currentTime);
    }
  };

  const handleLoadedMetadata = () => {
    if (videoRef.current) {
      setDuration(videoRef.current.duration);
    }
  };

  const handleSeek = (e) => {
    const seekTime = (e.target.value / 100) * duration;
    if (videoRef.current) {
      videoRef.current.currentTime = seekTime;
    }
  };

  const handleVolumeChange = (e) => {
    const newVolume = e.target.value;
    setVolume(newVolume);
    if (videoRef.current) {
      videoRef.current.volume = newVolume;
    }
  };

  const toggleFullscreen = () => {
    if (videoRef.current) {
      if (!isFullscreen) {
        videoRef.current.requestFullscreen();
      } else {
        document.exitFullscreen();
      }
      setIsFullscreen(!isFullscreen);
    }
  };

  const formatTime = (seconds) => {
    const hrs = Math.floor(seconds / 3600);
    const mins = Math.floor((seconds % 3600) / 60);
    const secs = Math.floor(seconds % 60);
    if (hrs > 0) {
      return `${hrs}:${mins.toString().padStart(2, '0')}:${secs.toString().padStart(2, '0')}`;
    }
    return `${mins}:${secs.toString().padStart(2, '0')}`;
  };

  return (
    <div className="video-player">
      <video
        ref={videoRef}
        src={videoUrl}
        onTimeUpdate={handleTimeUpdate}
        onLoadedMetadata={handleLoadedMetadata}
        onEnded={() => setIsPlaying(false)}
        className="video-element"
      />

      <div className="video-controls">
        <button onClick={togglePlay} className="control-button">
          {isPlaying ? '⏸' : '▶'}
        </button>

        <input
          type="range"
          min="0"
          max="100"
          value={(currentTime / duration) * 100 || 0}
          onChange={handleSeek}
          className="seek-bar"
        />

        <span className="time-display">
          {formatTime(currentTime)} / {formatTime(duration)}
        </span>

        <input
          type="range"
          min="0"
          max="1"
          step="0.1"
          value={volume}
          onChange={handleVolumeChange}
          className="volume-slider"
        />

        <span className="quality-badge">{quality}</span>

        <button onClick={toggleFullscreen} className="control-button">
          {isFullscreen ? '⛶' : '⛶'}
        </button>
      </div>
    </div>
  );
};

export default VideoPlayer;
