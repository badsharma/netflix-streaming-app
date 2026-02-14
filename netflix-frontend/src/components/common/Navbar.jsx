import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useAuth } from '../../context/AuthContext';
import '../../styles/Navbar.css';

const Navbar = () => {
  const { user, logout, isAuthenticated, isAdmin } = useAuth();
  const [showMenu, setShowMenu] = useState(false);
  const navigate = useNavigate();

  const handleLogout = () => {
    logout();
    navigate('/login');
  };

  return (
    <nav className="navbar">
      <div className="navbar-container">
        <Link to="/" className="navbar-logo">
          NetflixApp
        </Link>

        {isAuthenticated && (
          <div className="navbar-menu">
            <Link to="/" className="navbar-link">Home</Link>
            <Link to="/browse" className="navbar-link">Browse</Link>
            <Link to="/subscription" className="navbar-link">Subscription</Link>
            {isAdmin && (
              <Link to="/admin" className="navbar-link admin-link">Admin</Link>
            )}
          </div>
        )}

        <div className="navbar-right">
          {isAuthenticated ? (
            <div className="user-menu">
              <button
                className="user-button"
                onClick={() => setShowMenu(!showMenu)}
              >
                {user?.username || user?.email}
                <span className="user-tier">{user?.subscription?.tier}</span>
              </button>
              {showMenu && (
                <div className="dropdown-menu">
                  <Link to="/profile" className="dropdown-item">Profile</Link>
                  <button onClick={handleLogout} className="dropdown-item">
                    Logout
                  </button>
                </div>
              )}
            </div>
          ) : (
            <div className="auth-links">
              <Link to="/login" className="navbar-link">Login</Link>
              <Link to="/register" className="navbar-button">Sign Up</Link>
            </div>
          )}
        </div>
      </div>
    </nav>
  );
};

export default Navbar;
