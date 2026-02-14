import api from './api';

const subscriptionService = {
  getPlans: async () => {
    const response = await api.get('/subscriptions/plans');
    return response.data;
  },

  getCurrentSubscription: async () => {
    const response = await api.get('/subscriptions/current');
    return response.data;
  },

  changeSubscription: async (tier) => {
    const response = await api.post('/subscriptions/change', { tier });
    return response.data;
  }
};

export default subscriptionService;
