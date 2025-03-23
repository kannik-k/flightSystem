# Flight System

## Overview 
This is a web application built with Vue.js and Spring Boot that assists air travelers in planning their flights and provides personalized seat recommendations based on their preferences.

## Set Up

In order to run this project, you need the following tools:
- JDK 21
- Docker
- Docker Compose
- Node.js
- Gradle

### Backend
Clone the repository:

```bash
git clone <backend-repository-url>
```

Then run:

```bash
docker-compose up --build postgres
```

### Frontend
Clone the frontend repository:

```bash
git clone https://github.com/kannik-k/bookingSystem-frontend.git
```

Project Setup:

```bash
npm install
```

For development, run the following command to start the application with hot-reload:

```bash
npm run dev
```

For production, compile and minify the application with:

```bash
npm run build
```


## Documentation
The backend is fully documented with Swagger. To view the API documentation, ensure that the backend application is running, and navigate to http://localhost:8080/swagger-ui.html in your web browser.

## Frontend link
https://github.com/kannik-k/bookingSystem-frontend.git
  
## Original database shcema
![image](https://github.com/user-attachments/assets/348ffc8e-882a-4d7b-bacf-44b968ed24ef)

## Rules for simplyfing seat booking
To simplify seat allocation and planning, I will make some general assumptions.
1. Each plane has 33 rows.
2. Each row contains 6 seats (3 on the left and 3 on the right).
3. The first 4 rows are first class and have more legroom.
4. Rows 5 to 7 are business class and also have more legroom.
5. Row 14 has emergency exit doors and provides more legroom.
6. The first 5 rows and the last 5 rows are considered close to the exit.
7. First Class is priced at 1.5 times the base fare.
8. Business Class is priced at 1.2 times the base fare.
9. Economy Class is the base fare
10. One person can book up to 6 seats
11. Booked seats are random and are generated every time a client selects a flight.
