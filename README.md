# Quiz Application with Spring Boot

 **Quiz Application** built with Spring Boot, providing user authentication, quiz functionality, and score tracking. The application is deployed on **Heroku** using a **PostgreSQL** database.

---

## Features

### User Authentication
- **Login and Registration**:
  - Users can register with a username and password.
  - Login functionality with secure password handling (BCrypt encryption).
- **Session Management**:
  - Custom user details service integrated with Spring Security.

### Quiz Functionality
- **Dynamic Questions**:
  - Questions are fetched from an external API (`opentdb.com`) and displayed based on the selected difficulty.
- **Answer Validation**:
  - User answers are validated, and scores are updated in real-time.
- **Leaderboard**:
  - Displays the top 10 users based on their total scores.

### Technology Stack
- **Backend**:
  - Spring Boot
  - Spring Security
  - Spring Data JPA
- **Database**:
  - PostgreSQL
- **Frontend**:
  - Thymeleaf templates for dynamic HTML rendering, bootstrap for styling and javascript for frontend quiz functionality.
- **Deployment**:
  - Heroku

---

## Installation and Setup

1. **Clone the repository**:
   ```bash
   git clone https://github.com/your-username/quiz-app.git
   cd quiz-app
2. Set environment variables for the database connection:
    - export SPRING_DATASOURCE_URL=<your-database-url>
    - export SPRING_DATASOURCE_USERNAME=<your-username>
    - export SPRING_DATASOURCE_PASSWORD=<your-password>
    
3.Run the application:
 ```bash
./mvnw spring-boot:run


