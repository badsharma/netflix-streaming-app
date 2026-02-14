# 🚀 Netflix App - Quick Start Guide

## 📍 Project Location
**D:\projects\netflix-app**

---

## ⚡ Quick Start (3 Steps)

### Step 1: Start MongoDB
```bash
mongod
```

### Step 2: Start Backend (Terminal 1)
```bash
cd D:\projects\netflix-app\netflix-backend
mvn spring-boot:run
```
✅ Backend runs on: **http://localhost:8080**

### Step 3: Start Frontend (Terminal 2)
```bash
cd D:\projects\netflix-app\netflix-frontend

# First time only:
npm install
copy .env.example .env

# Start the app:
npm start
```
✅ Frontend runs on: **http://localhost:3000**

---

## 🎬 First Time Setup

### 1. Install Dependencies
```bash
cd D:\projects\netflix-app\netflix-frontend
npm install
```

### 2. Create Environment File
```bash
cd D:\projects\netflix-app\netflix-frontend
copy .env.example .env
```

The .env file will contain:
```
REACT_APP_API_BASE_URL=http://localhost:8080/api
REACT_APP_JWT_TOKEN_KEY=netflix_token
```

---

## 🧪 Testing the Application

### 1. Register a User
- Open http://localhost:3000
- Click "Sign Up"
- Create account → Gets **BASIC** subscription automatically

### 2. Create Admin User (via MongoDB)
```javascript
// Connect to MongoDB
mongo

// Switch to database
use netflix-streaming

// Make user admin
db.users.updateOne(
  { email: "youremail@test.com" },
  { $set: { role: "ADMIN" } }
)
```

### 3. Test Subscription Flow
1. Login as regular user
2. Browse movies (see BASIC tier only)
3. Go to Subscription page
4. Upgrade to **PREMIUM**
5. Browse again → See all movies!

---

## 📂 Project Structure

```
D:\projects\netflix-app\
├── 📁 netflix-backend/          # Spring Boot API
│   ├── src/main/java/
│   ├── pom.xml
│   └── README.md
│
├── 📁 netflix-frontend/         # React App
│   ├── src/
│   ├── package.json
│   └── README.md
│
├── README.md                    # Main documentation
├── IMPLEMENTATION_GUIDE.md      # Technical guide
└── QUICK_START.md              # This file
```

---

## 🔧 Common Commands

### Backend
```bash
cd D:\projects\netflix-app\netflix-backend

# Run
mvn spring-boot:run

# Build
mvn clean install

# Test
mvn test
```

### Frontend
```bash
cd D:\projects\netflix-app\netflix-frontend

# Install dependencies
npm install

# Run development server
npm start

# Build for production
npm run build

# Run tests
npm test
```

---

## 🌐 Access Points

| Service | URL |
|---------|-----|
| Frontend | http://localhost:3000 |
| Backend API | http://localhost:8080 |
| API Docs | http://localhost:8080/api |
| MongoDB | mongodb://localhost:27017 |

---

## 📊 Subscription Tiers

| Tier | Quality | Devices | Movies |
|------|---------|---------|---------|
| BASIC | SD | 1 | 100 |
| STANDARD | SD, HD | 2 | 500 |
| PREMIUM | SD, HD, 4K | 4 | Unlimited |

---

## 🐛 Troubleshooting

### Backend won't start
- Ensure MongoDB is running: `mongod`
- Check port 8080 is free
- Verify Java 17+ is installed

### Frontend won't start
- Run `npm install` first
- Check `.env` file exists
- Ensure backend is running on port 8080

### Can't login
- Clear localStorage in browser
- Check backend logs for errors
- Verify MongoDB is accessible

---

## 💡 Pro Tips

1. **Use 3 Terminals**: MongoDB, Backend, Frontend
2. **Check Logs**: Backend logs show all API requests
3. **Use Browser DevTools**: Check Network tab for API calls
4. **MongoDB Compass**: GUI to view database contents
5. **Postman**: Test API endpoints directly

---

## 📚 Documentation

- **Main README**: `README.md`
- **Backend Guide**: `netflix-backend/README.md`
- **Frontend Guide**: `netflix-frontend/README.md`
- **Implementation Details**: `IMPLEMENTATION_GUIDE.md`

---

## ✅ Checklist

Before starting:
- [ ] MongoDB installed and running
- [ ] Java 17+ installed
- [ ] Node.js and npm installed
- [ ] Maven installed
- [ ] Project at `D:\projects\netflix-app`

First run:
- [ ] Run `npm install` in frontend
- [ ] Create `.env` file from `.env.example`
- [ ] Start MongoDB
- [ ] Start backend
- [ ] Start frontend

---

**Ready to go! Visit http://localhost:3000 to start streaming!** 🎬🍿
