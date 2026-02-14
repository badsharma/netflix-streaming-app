import React, { useEffect, useState } from 'react';
import { useSubscription } from '../../context/SubscriptionContext';
import PlanCard from './PlanCard';
import '../../styles/Subscription.css';

const SubscriptionPlans = () => {
  const { plans, subscription, changeSubscription, loading } = useSubscription();
  const [selectedTier, setSelectedTier] = useState(null);
  const [error, setError] = useState('');
  const [success, setSuccess] = useState('');

  useEffect(() => {
    if (subscription) {
      setSelectedTier(subscription.tier);
    }
  }, [subscription]);

  const handleSubscribe = async (tier) => {
    if (tier === subscription?.tier) {
      setError('You already have this subscription');
      return;
    }

    setError('');
    setSuccess('');

    try {
      await changeSubscription(tier);
      setSuccess('Subscription updated successfully!');
      setSelectedTier(tier);
    } catch (err) {
      setError(err.response?.data?.message || 'Failed to update subscription');
    }
  };

  return (
    <div className="subscription-plans">
      <h1>Choose Your Plan</h1>
      <p className="plans-subtitle">
        Upgrade or downgrade your subscription anytime
      </p>

      {error && <div className="error-message">{error}</div>}
      {success && <div className="success-message">{success}</div>}

      <div className="plans-grid">
        {plans.map((plan) => (
          <PlanCard
            key={plan.tier}
            plan={plan}
            isCurrentPlan={plan.tier === subscription?.tier}
            onSelect={() => handleSubscribe(plan.tier)}
            loading={loading}
          />
        ))}
      </div>
    </div>
  );
};

export default SubscriptionPlans;
