export const API_BASE_URL = process.env.REACT_APP_API_BASE_URL || 'http://localhost:8080/api';
export const JWT_TOKEN_KEY = process.env.REACT_APP_JWT_TOKEN_KEY || 'netflix_token';

export const SUBSCRIPTION_TIERS = {
  BASIC: 'BASIC',
  STANDARD: 'STANDARD',
  PREMIUM: 'PREMIUM'
};

export const VIDEO_QUALITIES = {
  SD: 'SD',
  HD: 'HD',
  UHD_4K: 'UHD_4K'
};

export const GENRES = [
  'Action',
  'Comedy',
  'Drama',
  'Horror',
  'Romance',
  'Sci-Fi',
  'Thriller',
  'Documentary',
  'Animation',
  'Fantasy'
];
