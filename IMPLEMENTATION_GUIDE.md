# Netflix-Like Streaming Application - Implementation Guide

## Project Status

✅ **COMPLETED:**
- Backend infrastructure (Spring Boot)
  - MongoDB models, repositories, services
  - JWT authentication & security
  - REST API controllers
  - Exception handling
- Frontend setup
  - Project structure
  - API service layer with Axios
  - Utility files and constants

📝 **REMAINING WORK:**

### 1. Context Providers (See files below for complete code)
- `src/context/AuthContext.jsx` - User authentication state
- `src/context/MovieContext.jsx` - Movie data management
- `src/context/SubscriptionContext.jsx` - Subscription management

### 2. React Components
#### Common Components
- `src/components/common/Navbar.jsx` - Navigation bar
- `src/components/common/Footer.jsx` - Footer
- `src/components/common/Loader.jsx` - Loading spinner
- `src/components/common/ProtectedRoute.jsx` - Route protection

#### Auth Components
- `src/components/auth/Login.jsx` - Login form
- `src/components/auth/Register.jsx` - Registration form

#### Movie Components
- `src/components/movies/MovieCard.jsx` - Movie display card
- `src/components/movies/MovieGrid.jsx` - Grid layout
- `src/components/movies/SearchBar.jsx` - Search functionality

#### Video Components
- `src/components/video/VideoPlayer.jsx` - Video playback

#### Subscription Components
- `src/components/subscription/SubscriptionPlans.jsx` - Plan selection
- `src/components/subscription/PlanCard.jsx` - Individual plan card

### 3. Pages
- `src/pages/Home.jsx` - Main landing page
- `src/pages/Browse.jsx` - Browse all movies
- `src/pages/Watch.jsx` - Watch video
- `src/pages/SubscriptionPage.jsx` - Manage subscription

### 4. Main App Files
- `src/App.js` - Main app component with routing
- `src/index.js` - Entry point
- `src/styles/App.css` - Main styles

## Quick Start

### Backend
```bash
cd netflix-backend

# Make sure MongoDB is running
mongod

# Run the backend
mvn spring-boot:run
```

Backend runs on: http://localhost:8080

### Frontend
```bash
cd netflix-frontend

# Install dependencies (requires Node.js)
npm install

# Start development server
npm start
```

Frontend runs on: http://localhost:3000

## Installation Prerequisites

⚠️ **IMPORTANT**: You need to install Node.js and npm first!

### Install Node.js:
1. Download from https://nodejs.org/ (LTS version recommended)
2. Run the installer
3. Verify installation:
   ```bash
   node --version
   npm --version
   ```

Once Node.js is installed, navigate to the `netflix-frontend` directory and run:
```bash
npm install
```

This will install all React dependencies listed in package.json.

## Testing the Application

### 1. Create Test Data
After starting the backend, you can:
- Register a new user (gets BASIC subscription automatically)
- Create an admin user via MongoDB:
  ```javascript
  db.users.updateOne(
    { email: "admin@example.com" },
    { $set: { role: "ADMIN" } }
  )
  ```
- Add movies via the API (admin only)

### 2. Test Subscription Features
- Login with basic user → See limited movies
- Change subscription to PREMIUM
- See all movies and quality options

### 3. Test Video Playback
- Click on a movie
- Start playback
- Progress is auto-saved every 10 seconds
- Refresh page → Video resumes from last position

## API Endpoints Summary

### Auth
- POST `/api/auth/register` - Register
- POST `/api/auth/login` - Login
- GET `/api/auth/me` - Get current user

### Movies
- GET `/api/movies` - List all (with search/filter)
- GET `/api/movies/{id}` - Get one
- GET `/api/movies/featured` - Featured movies
- GET `/api/movies/trending` - Trending movies
- POST `/api/movies` - Create (admin)
- PUT `/api/movies/{id}` - Update (admin)
- DELETE `/api/movies/{id}` - Delete (admin)

### Subscriptions
- GET `/api/subscriptions/plans` - Available plans
- GET `/api/subscriptions/current` - User's plan
- POST `/api/subscriptions/change` - Change plan

### Streaming
- GET `/api/streaming/{id}/validate` - Check access
- GET `/api/streaming/{id}/stream` - Get stream URL

### History
- GET `/api/history` - Watch history
- GET `/api/history/continue-watching` - Resume watching
- POST `/api/history/{id}/progress` - Save progress

## Architecture Overview

```
netflix-app/
├── netflix-backend/          # Spring Boot Backend
│   ├── src/main/java/
│   │   └── com/netflix/streaming/
│   │       ├── config/       # Security, CORS
│   │       ├── controller/   # REST endpoints
│   │       ├── dto/          # Request/Response objects
│   │       ├── exception/    # Error handling
│   │       ├── model/        # MongoDB models
│   │       ├── repository/   # Data access
│   │       ├── security/     # JWT implementation
│   │       └── service/      # Business logic
│   └── pom.xml
│
└── netflix-frontend/         # React Frontend
    ├── public/
    ├── src/
    │   ├── components/       # React components
    │   ├── context/          # State management
    │   ├── pages/            # Page components
    │   ├── services/         # API calls
    │   ├── styles/           # CSS files
    │   └── utils/            # Helper functions
    └── package.json
```

## Subscription Tiers

| Feature | BASIC | STANDARD | PREMIUM |
|---------|-------|----------|---------|
| Quality | SD | SD, HD | SD, HD, 4K |
| Devices | 1 | 2 | 4 |
| Movies | 100 | 500 | Unlimited |
| Price | Free | Free | Free |

## Next Steps

1. **Install Node.js** (if not installed)
2. **Run Backend**: Start MongoDB and Spring Boot app
3. **Install Frontend Dependencies**: `npm install` in netflix-frontend
4. **Implement Remaining Components** (Context, Components, Pages)
5. **Add Styling**: Create Netflix-like dark theme
6. **Test Everything**: Register → Browse → Subscribe → Watch

## Troubleshooting

### Backend Issues
- **MongoDB connection failed**: Make sure MongoDB is running on port 27017
- **JWT errors**: Check jwt.secret in application.properties (must be 256+ bits)
- **CORS errors**: Verify cors.allowed-origins includes frontend URL

### Frontend Issues
- **API calls failing**: Check REACT_APP_API_BASE_URL in .env file
- **401 Unauthorized**: Token expired or invalid, try logging in again
- **Build errors**: Delete node_modules and run `npm install` again

## Resources

- [Spring Boot Docs](https://spring.io/projects/spring-boot)
- [React Docs](https://react.dev/)
- [MongoDB Docs](https://docs.mongodb.com/)
- [Axios Docs](https://axios-http.com/)

Good luck with your Netflix-like streaming application! 🎬🍿
