import React, { createContext, useState, useContext, useEffect } from 'react';
import subscriptionService from '../services/subscriptionService';
import { useAuth } from './AuthContext';

const SubscriptionContext = createContext();

export const useSubscription = () => {
  const context = useContext(SubscriptionContext);
  if (!context) {
    throw new Error('useSubscription must be used within SubscriptionProvider');
  }
  return context;
};

export const SubscriptionProvider = ({ children }) => {
  const { user, isAuthenticated } = useAuth();
  const [subscription, setSubscription] = useState(null);
  const [plans, setPlans] = useState([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    if (isAuthenticated && user) {
      setSubscription(user.subscription);
      loadPlans();
    }
  }, [isAuthenticated, user]);

  const loadPlans = async () => {
    try {
      const data = await subscriptionService.getPlans();
      setPlans(data);
    } catch (error) {
      console.error('Failed to load plans:', error);
    }
  };

  const changeSubscription = async (tier) => {
    setLoading(true);
    try {
      const updated = await subscriptionService.changeSubscription(tier);
      setSubscription(updated);
      return updated;
    } catch (error) {
      throw error;
    } finally {
      setLoading(false);
    }
  };

  const canWatchQuality = (quality) => {
    if (!subscription) return false;
    return subscription.allowedQualities?.includes(quality);
  };

  const value = {
    subscription,
    plans,
    loading,
    changeSubscription,
    canWatchQuality
  };

  return (
    <SubscriptionContext.Provider value={value}>
      {children}
    </SubscriptionContext.Provider>
  );
};

export default SubscriptionContext;
