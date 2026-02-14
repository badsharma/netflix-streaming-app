# Netflix-Like Streaming Application

A full-stack Netflix-inspired streaming application with subscription-based access control, built with Spring Boot and React.

## 🎯 Overview

This application allows users to browse and watch movies based on their subscription tier. Features include JWT authentication, role-based access control, video streaming with quality restrictions, and watch progress tracking.

## ✨ Features

### Backend (Spring Boot)
- ✅ User authentication with JWT
- ✅ Role-based access control (USER, ADMIN)
- ✅ Three subscription tiers (BASIC, STANDARD, PREMIUM)
- ✅ Movie CRUD operations
- ✅ Video streaming with quality restrictions
- ✅ Watch progress tracking
- ✅ RESTful API design

### Frontend (React)
- ✅ User authentication (Login/Register)
- ✅ Movie browsing with search and filters
- ✅ Custom video player
- ✅ Subscription management
- ✅ Watch progress auto-save
- ✅ Netflix-like dark theme
- ✅ Responsive design

## 🏗️ Architecture

```
netflix-app/
├── netflix-backend/        # Spring Boot REST API
│   ├── src/main/java/
│   │   └── com/netflix/streaming/
│   │       ├── config/     # Security, CORS
│   │       ├── controller/ # REST endpoints
│   │       ├── dto/        # Request/Response objects
│   │       ├── exception/  # Error handling
│   │       ├── model/      # MongoDB models
│   │       ├── repository/ # Data access
│   │       ├── security/   # JWT implementation
│   │       └── service/    # Business logic
│   └── pom.xml
│
└── netflix-frontend/       # React Application
    ├── src/
    │   ├── components/     # React components
    │   ├── context/        # State management
    │   ├── pages/          # Page components
    │   ├── services/       # API calls
    │   ├── styles/         # CSS files
    │   └── utils/          # Helper functions
    └── package.json
```

## 🚀 Quick Start

### Prerequisites

1. **Java 17+** - Backend runtime
2. **Maven 3.6+** - Build tool
3. **MongoDB 4.0+** - Database
4. **Node.js 16+** - Frontend runtime
5. **npm** - Package manager

### Installation Steps

#### 1. Start MongoDB
```bash
mongod
```

#### 2. Backend Setup
```bash
cd netflix-backend

# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

Backend runs on: **http://localhost:8080**

#### 3. Frontend Setup
```bash
cd netflix-frontend

# Install dependencies
npm install

# Create environment file
cp .env.example .env

# Start development server
npm start
```

Frontend runs on: **http://localhost:3000**

## 📋 Subscription Tiers

| Feature | BASIC | STANDARD | PREMIUM |
|---------|-------|----------|---------|
| **Video Quality** | SD only | SD, HD | SD, HD, 4K |
| **Devices** | 1 | 2 | 4 |
| **Movie Access** | 100 movies | 500 movies | Unlimited |
| **Price** | Free (Demo) | Free (Demo) | Free (Demo) |

## 🔌 API Endpoints

### Authentication
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Login
- `GET /api/auth/me` - Get current user

### Movies
- `GET /api/movies` - Get all movies (with search/filter)
- `GET /api/movies/{id}` - Get movie by ID
- `GET /api/movies/featured` - Get featured movies
- `GET /api/movies/trending` - Get trending movies
- `POST /api/movies` - Create movie (Admin only)
- `PUT /api/movies/{id}` - Update movie (Admin only)
- `DELETE /api/movies/{id}` - Delete movie (Admin only)

### Subscriptions
- `GET /api/subscriptions/plans` - Get available plans
- `GET /api/subscriptions/current` - Get current subscription
- `POST /api/subscriptions/change` - Change subscription tier

### Streaming
- `GET /api/streaming/{id}/validate` - Validate streaming access
- `GET /api/streaming/{id}/stream` - Get stream URL

### Watch History
- `GET /api/history` - Get watch history
- `GET /api/history/continue-watching` - Get continue watching list
- `POST /api/history/{id}/progress` - Save watch progress

## 🧪 Testing

### Create Test User

1. **Register a new user:**
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"email":"user@test.com","username":"testuser","password":"password123"}'
```

User automatically gets BASIC subscription.

2. **Create Admin User (via MongoDB):**
```javascript
db.users.updateOne(
  { email: "admin@test.com" },
  { $set: { role: "ADMIN" } }
)
```

### Add Sample Movies (Admin required)

```bash
curl -X POST http://localhost:8080/api/movies \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_JWT_TOKEN" \
  -d '{
    "title": "Sample Movie",
    "description": "A great movie",
    "genres": ["Action", "Drama"],
    "releaseYear": 2024,
    "thumbnailUrl": "https://example.com/thumb.jpg",
    "videoUrl": "https://example.com/video.mp4",
    "minimumTier": "BASIC",
    "durationMinutes": 120,
    "rating": 8.5,
    "featured": true,
    "trending": false
  }'
```

## 🔧 Configuration

### Backend (application.properties)
```properties
server.port=8080
spring.data.mongodb.database=netflix-streaming
jwt.secret=your-secret-key-256-bits-minimum
jwt.expiration=86400000
cors.allowed-origins=http://localhost:3000
```

### Frontend (.env)
```
REACT_APP_API_BASE_URL=http://localhost:8080/api
REACT_APP_JWT_TOKEN_KEY=netflix_token
```

## 📱 Usage Flow

1. **Register** → Auto-assigned BASIC subscription
2. **Login** → Get JWT token
3. **Browse Movies** → See movies based on subscription tier
4. **Upgrade Subscription** → Access more movies and better quality
5. **Watch Movie** → Video player with quality selection
6. **Progress Tracking** → Resume from where you left off

## 🛠️ Technologies Used

### Backend
- Spring Boot 3.2.1
- Spring Security
- Spring Data MongoDB
- JWT (jjwt 0.11.5)
- Lombok

### Frontend
- React 18
- React Router DOM 6
- Axios
- Context API

### Database
- MongoDB

## 📚 Documentation

- [Backend README](netflix-backend/README.md)
- [Frontend README](netflix-frontend/README.md)
- [Implementation Guide](IMPLEMENTATION_GUIDE.md)

## 🐛 Troubleshooting

### Backend Issues
- **MongoDB not found**: Ensure MongoDB is running on port 27017
- **JWT errors**: Check jwt.secret in application.properties (256+ bits)
- **CORS errors**: Verify cors.allowed-origins includes frontend URL

### Frontend Issues
- **API calls failing**: Check backend is running and .env is configured
- **401 Unauthorized**: JWT token expired, log in again
- **Build errors**: Delete node_modules and run `npm install`

## 📄 License

This project is for educational purposes.

## 🙏 Acknowledgments

- Inspired by Netflix
- Built with Spring Boot and React
- Uses MongoDB for data persistence

## 📧 Support

For issues or questions, please create an issue in the repository.

---

**Happy Streaming! 🎬🍿**
