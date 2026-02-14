import api from './api';
import { JWT_TOKEN_KEY } from '../utils/constants';

const authService = {
  register: async (email, username, password) => {
    const response = await api.post('/auth/register', { email, username, password });
    if (response.data.token) {
      localStorage.setItem(JWT_TOKEN_KEY, response.data.token);
    }
    return response.data;
  },

  login: async (email, password) => {
    const response = await api.post('/auth/login', { email, password });
    if (response.data.token) {
      localStorage.setItem(JWT_TOKEN_KEY, response.data.token);
    }
    return response.data;
  },

  logout: () => {
    localStorage.removeItem(JWT_TOKEN_KEY);
  },

  getCurrentUser: async () => {
    const response = await api.get('/auth/me');
    return response.data;
  },

  isAuthenticated: () => {
    return !!localStorage.getItem(JWT_TOKEN_KEY);
  }
};

export default authService;
