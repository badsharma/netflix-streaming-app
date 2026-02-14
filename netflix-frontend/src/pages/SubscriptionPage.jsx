import React from 'react';
import { useSubscription } from '../context/SubscriptionContext';
import SubscriptionPlans from '../components/subscription/SubscriptionPlans';
import '../styles/SubscriptionPage.css';

const SubscriptionPage = () => {
  const { subscription } = useSubscription();

  return (
    <div className="subscription-page">
      {subscription && (
        <div className="current-subscription-info">
          <h2>Your Current Plan</h2>
          <div className="current-plan-details">
            <div className="plan-tier">
              <span className="tier-name">{subscription.tier}</span>
              <span className="tier-status">
                {subscription.active ? 'Active' : 'Inactive'}
              </span>
            </div>
            <div className="plan-benefits">
              <p>Max Devices: {subscription.maxDevices}</p>
              <p>Quality: {subscription.allowedQualities?.join(', ')}</p>
              {subscription.maxMovieAccess && (
                <p>Movie Access: Up to {subscription.maxMovieAccess} movies</p>
              )}
              {!subscription.maxMovieAccess && (
                <p>Movie Access: Unlimited</p>
              )}
            </div>
          </div>
        </div>
      )}

      <SubscriptionPlans />
    </div>
  );
};

export default SubscriptionPage;
