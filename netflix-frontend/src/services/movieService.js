import api from './api';

const movieService = {
  getAllMovies: async (page = 0, size = 20, search = '', genre = '') => {
    const params = { page, size };
    if (search) params.search = search;
    if (genre) params.genre = genre;
    const response = await api.get('/movies', { params });
    return response.data;
  },

  getMovieById: async (id) => {
    const response = await api.get(`/movies/${id}`);
    return response.data;
  },

  getFeaturedMovies: async () => {
    const response = await api.get('/movies/featured');
    return response.data;
  },

  getTrendingMovies: async () => {
    const response = await api.get('/movies/trending');
    return response.data;
  },

  createMovie: async (movieData) => {
    const response = await api.post('/movies', movieData);
    return response.data;
  },

  updateMovie: async (id, movieData) => {
    const response = await api.put(`/movies/${id}`, movieData);
    return response.data;
  },

  deleteMovie: async (id) => {
    const response = await api.delete(`/movies/${id}`);
    return response.data;
  }
};

export default movieService;
