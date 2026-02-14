import React from 'react';
import '../../styles/Subscription.css';

const PlanCard = ({ plan, isCurrentPlan, onSelect, loading }) => {
  return (
    <div className={`plan-card ${isCurrentPlan ? 'current-plan' : ''}`}>
      <h2 className="plan-name">{plan.name}</h2>
      <div className="plan-price">{plan.price}</div>

      <ul className="plan-features">
        {plan.features.map((feature, index) => (
          <li key={index}>✓ {feature}</li>
        ))}
        <li>✓ {plan.maxDevices} {plan.maxDevices === 1 ? 'device' : 'devices'}</li>
        <li>
          ✓ {plan.qualities.join(', ')} quality
        </li>
        {plan.maxMovies && (
          <li>✓ Access to {plan.maxMovies} movies</li>
        )}
        {!plan.maxMovies && (
          <li>✓ Unlimited movie access</li>
        )}
      </ul>

      <button
        onClick={onSelect}
        disabled={isCurrentPlan || loading}
        className={`plan-button ${isCurrentPlan ? 'current' : ''}`}
      >
        {isCurrentPlan ? 'Current Plan' : loading ? 'Updating...' : 'Select Plan'}
      </button>
    </div>
  );
};

export default PlanCard;
