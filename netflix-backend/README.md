# Netflix Streaming Application - Backend

A Spring Boot backend for a Netflix-like streaming application with subscription-based access control.

## Features

- User authentication with JWT
- Role-based access control (USER, ADMIN)
- Three subscription tiers (Basic, Standard, Premium)
- Movie catalog management
- Video streaming with quality restrictions
- Watch history and progress tracking

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- MongoDB 4.0+ running on localhost:27017

## Getting Started

### 1. Start MongoDB

```bash
mongod
```

### 2. Build the project

```bash
mvn clean install
```

### 3. Run the application

```bash
mvn spring-boot:run
```

The backend will start on `http://localhost:8080`

## API Endpoints

### Authentication
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Login
- `GET /api/auth/me` - Get current user

### Movies
- `GET /api/movies` - Get all movies (with filtering)
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
- `GET /api/streaming/{movieId}/validate` - Validate streaming access
- `GET /api/streaming/{movieId}/stream` - Get stream URL

### Watch History
- `GET /api/history` - Get watch history
- `GET /api/history/continue-watching` - Get continue watching list
- `POST /api/history/{movieId}/progress` - Save watch progress
- `GET /api/history/{movieId}` - Get watch progress for a movie

## Configuration

Edit `src/main/resources/application.properties` to configure:
- MongoDB connection
- JWT secret and expiration
- CORS allowed origins
- Server port

## Default Users

You can create an admin user by registering and manually updating the role in MongoDB:

```javascript
db.users.updateOne(
    { email: "admin@example.com" },
    { $set: { role: "ADMIN" } }
)
```

## Subscription Tiers

| Tier | Quality | Max Devices | Movie Access |
|------|---------|-------------|--------------|
| BASIC | SD | 1 | Limited |
| STANDARD | SD, HD | 2 | More movies |
| PREMIUM | SD, HD, 4K | 4 | All movies |
