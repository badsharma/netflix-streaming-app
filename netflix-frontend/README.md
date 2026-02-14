# Netflix Streaming Application - Frontend

React-based frontend for a Netflix-like streaming application with subscription-based access control.

## Features

- 🔐 User authentication (Login/Register)
- 🎬 Movie browsing with search and filters
- 📺 Video player with quality selection
- 💳 Subscription management (Basic, Standard, Premium)
- ⏯️ Watch progress tracking and resume
- 📱 Responsive design
- 🎨 Netflix-like dark theme

## Prerequisites

- Node.js 16+ and npm
- Backend API running on http://localhost:8080

## Installation

1. **Install dependencies:**
```bash
npm install
```

2. **Create .env file:**
```bash
cp .env.example .env
```

Edit `.env` if needed:
```
REACT_APP_API_BASE_URL=http://localhost:8080/api
REACT_APP_JWT_TOKEN_KEY=netflix_token
```

3. **Start development server:**
```bash
npm start
```

App runs on: http://localhost:3000

## Project Structure

```
src/
├── components/          # React components
│   ├── auth/           # Login, Register
│   ├── common/         # Navbar, Loader, ProtectedRoute
│   ├── movies/         # MovieCard, MovieGrid, SearchBar
│   ├── video/          # VideoPlayer
│   └── subscription/   # SubscriptionPlans, PlanCard
├── context/            # State management
│   ├── AuthContext.jsx
│   ├── MovieContext.jsx
│   └── SubscriptionContext.jsx
├── pages/              # Page components
│   ├── Home.jsx
│   ├── Browse.jsx
│   ├── Watch.jsx
│   └── SubscriptionPage.jsx
├── services/           # API calls
│   ├── api.js          # Axios instance
│   ├── authService.js
│   ├── movieService.js
│   ├── subscriptionService.js
│   └── watchHistoryService.js
├── styles/             # CSS files
├── utils/              # Helper functions
│   └── constants.js
├── App.js              # Main app component
└── index.js            # Entry point
```

## Available Scripts

- `npm start` - Run development server
- `npm build` - Build for production
- `npm test` - Run tests
- `npm eject` - Eject from Create React App

## Key Features Explained

### Authentication
- JWT token stored in localStorage
- Auto-redirect to login if unauthenticated
- Protected routes for authenticated users

### Subscription System
- **Basic**: SD quality, 1 device, limited movies
- **Standard**: HD quality, 2 devices, more movies
- **Premium**: 4K quality, 4 devices, all movies

### Video Player
- Custom controls (play, pause, seek, volume)
- Quality selection based on subscription
- Auto-save progress every 10 seconds
- Resume from last position

### State Management
- **AuthContext**: User authentication state
- **MovieContext**: Movie data and operations
- **SubscriptionContext**: Subscription info

## API Integration

All API calls go through `src/services/api.js`:
- Automatically adds JWT token to requests
- Handles 401 (unauthorized) errors
- Redirects to login on authentication failure

## Routing

| Route | Component | Protection |
|-------|-----------|-----------|
| `/` | Home | Protected |
| `/login` | Login | Public |
| `/register` | Register | Public |
| `/browse` | Browse | Protected |
| `/watch/:id` | Watch | Protected |
| `/subscription` | SubscriptionPage | Protected |

## Styling

Netflix-inspired dark theme with:
- Background: #141414
- Primary: #e50914 (Netflix red)
- Hover effects and transitions
- Responsive grid layouts
- Mobile-friendly design

## Environment Variables

| Variable | Default | Description |
|----------|---------|-------------|
| REACT_APP_API_BASE_URL | http://localhost:8080/api | Backend API URL |
| REACT_APP_JWT_TOKEN_KEY | netflix_token | LocalStorage key for JWT |

## Troubleshooting

### Dependencies won't install
```bash
rm -rf node_modules package-lock.json
npm install
```

### API calls failing
- Check backend is running on port 8080
- Verify REACT_APP_API_BASE_URL in .env
- Check browser console for errors

### Can't login
- Clear localStorage
- Check backend logs for errors
- Verify MongoDB is running

### Video won't play
- Check subscription tier matches movie requirement
- Verify stream URL is valid
- Check browser console for errors

## Production Build

1. **Build optimized production bundle:**
```bash
npm run build
```

2. **Serve with static server:**
```bash
npm install -g serve
serve -s build -p 3000
```

## Browser Support

- Chrome (latest)
- Firefox (latest)
- Safari (latest)
- Edge (latest)

## Technologies Used

- **React 18** - UI library
- **React Router DOM 6** - Routing
- **Axios** - HTTP client
- **Context API** - State management
- **CSS3** - Styling

## Contributing

1. Create feature branch
2. Make changes
3. Test thoroughly
4. Submit pull request

## License

This project is for educational purposes.
