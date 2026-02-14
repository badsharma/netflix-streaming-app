import api from './api';

const watchHistoryService = {
  getWatchHistory: async (page = 0, size = 20) => {
    const response = await api.get('/history', { params: { page, size } });
    return response.data;
  },

  getContinueWatching: async () => {
    const response = await api.get('/history/continue-watching');
    return response.data;
  },

  saveWatchProgress: async (movieId, progressSeconds, totalDurationSeconds, quality) => {
    const response = await api.post(`/history/${movieId}/progress`, {
      progressSeconds,
      totalDurationSeconds,
      quality
    });
    return response.data;
  },

  getWatchProgress: async (movieId) => {
    const response = await api.get(`/history/${movieId}`);
    return response.data;
  },

  deleteWatchHistory: async (movieId) => {
    const response = await api.delete(`/history/${movieId}`);
    return response.data;
  },

  validateStreamingAccess: async (movieId, quality = null) => {
    const params = quality ? { quality } : {};
    const response = await api.get(`/streaming/${movieId}/validate`, { params });
    return response.data;
  },

  getStreamUrl: async (movieId, quality = 'SD') => {
    const response = await api.get(`/streaming/${movieId}/stream`, { params: { quality } });
    return response.data;
  }
};

export default watchHistoryService;
