import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import { AuthProvider } from './context/AuthContext';
import { MovieProvider } from './context/MovieContext';
import { SubscriptionProvider } from './context/SubscriptionContext';
import Navbar from './components/common/Navbar';
import ProtectedRoute from './components/common/ProtectedRoute';
import Login from './components/auth/Login';
import Register from './components/auth/Register';
import Home from './pages/Home';
import Browse from './pages/Browse';
import Watch from './pages/Watch';
import SubscriptionPage from './pages/SubscriptionPage';
import './styles/App.css';

function App() {
  return (
    <AuthProvider>
      <MovieProvider>
        <SubscriptionProvider>
          <Router>
            <div className="App">
              <Navbar />
              <div className="app-content">
                <Routes>
                  {/* Public Routes */}
                  <Route path="/login" element={<Login />} />
                  <Route path="/register" element={<Register />} />

                  {/* Protected Routes */}
                  <Route
                    path="/"
                    element={
                      <ProtectedRoute>
                        <Home />
                      </ProtectedRoute>
                    }
                  />
                  <Route
                    path="/browse"
                    element={
                      <ProtectedRoute>
                        <Browse />
                      </ProtectedRoute>
                    }
                  />
                  <Route
                    path="/watch/:id"
                    element={
                      <ProtectedRoute>
                        <Watch />
                      </ProtectedRoute>
                    }
                  />
                  <Route
                    path="/subscription"
                    element={
                      <ProtectedRoute>
                        <SubscriptionPage />
                      </ProtectedRoute>
                    }
                  />

                  {/* Redirect */}
                  <Route path="*" element={<Navigate to="/" replace />} />
                </Routes>
              </div>
            </div>
          </Router>
        </SubscriptionProvider>
      </MovieProvider>
    </AuthProvider>
  );
}

export default App;
