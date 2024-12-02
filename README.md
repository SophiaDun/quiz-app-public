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

# How to Deploy to Heroku

This guide walks you through the process of deploying a Java-based project (e.g., Spring Boot) to Heroku, using GitHub as your repository.


## Steps to Deploy to Heroku

### 1. Set up Your GitHub Repository

- Ensure that your project is in a GitHub repository, and the necessary files (e.g., `pom.xml` for Maven-based projects) are at the root level of your repository.

### 2. Create an Account on Heroku

- Go to [Heroku's website](https://www.heroku.com/) and create an account if you don’t have one already.

### 3. Create a New App on Heroku

- Visit [Heroku Dashboard](https://dashboard.heroku.com/new-app) and click on > **Create New App**.
- Follow the prompts to create a new app, such as naming your app and selecting your region.

### 4. Connect Heroku to GitHub

- After creating your app, you’ll be redirected to the deployment page.
- **Deployment Methods**: Heroku offers several methods for deployment, but one of the simplest ways is to connect your GitHub repository directly.
- In the **Deployment Method** section, click on **GitHub** and then **Connect to GitHub**.

### 5. Connect Your GitHub Repository

- Once connected to GitHub, a search dialog will appear where you can search for your repository.
- Type the name of your repository and select the correct one. Click **Connect**.

### 6. Deploy Your App

- After connecting your repository, you’ll have two options for deployment:
  - **Enable automatic deploys** from GitHub.
  - **Manual deploy** a specific branch from GitHub.

  Choose the deployment method that works best for your needs.

### 7. Verify Deployment

- After deployment, Heroku will show the status of your build. If everything goes well, you’ll see green checkmarks indicating that the build and release have succeeded.
- A URL will be provided to access your deployed app. Click on **View** to open your app.

## VS Code Configuration for PostgreSQL

To configure PostgreSQL in your Spring Boot project, follow these steps:

### 1. Add PostgreSQL Dependency to `pom.xml`

In your `pom.xml`, add the following dependency for PostgreSQL:

```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
    <version>42.5.0</version>
</dependency>
```
### 2. Remove H2 Database Dependency
If you were previously using H2 (a lightweight database) for development, remove that dependency from your pom.xml.

## 3. Configure PostgreSQL on Heroku
In your Heroku app’s dashboard, go to Resources.
Under the Add-Ons field, search for Heroku Postgres and select it.
Choose the Free Plan or any desired plan, and click Provision.
After adding the PostgreSQL add-on, go to Settings > View Credentials to view the database credentials.

## 4. Configure application.properties
Copy the database credentials provided by Heroku and add them to your application.properties file in the following format:
```
spring.datasource.url=${SPRING_DATASOURCE_URL}
spring.datasource.username=${SPRING_DATASOURCE_USERNAME}
spring.datasource.password=${SPRING_DATASOURCE_PASSWORD}
```
Heroku will automatically populate these environment variables for you. You don’t need to explicitly add the credentials; Heroku does this for you.

## 5. Use Environment Variables for Security
To keep your database credentials secure, do not publish them directly to GitHub. Heroku uses environment variables to securely manage your database credentials.

## 6. Access Your Database
Once your PostgreSQL add-on is provisioned, you can connect to the database using the credentials provided by Heroku. These will be automatically available to your Spring Boot application.

