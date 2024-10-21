This project is a weather monitoring system that fetches real-time weather data from the OpenWeather API and stores it in a database.
The frontend provides a user interface to display weather data and summaries for various cities, while the backend handles API requests, data fetching, and storage.

# clone the repsitory
  git clone https://github.com/shivang821/Weather-Monitoring.git
# Frontend Setup
  Prerequisites
  Node.js
  npm or yarn
  
  ## Installation
  1. Switch to frontend:
     cd frontend
  2. Install dependencies:
     npm install
     or
     yarn install
  3. Start the frontend server after starting backend server :
     npm run dev
     or
     yarn dev
     
     The frontend will run on http://localhost:3000.

# Backend Setup
  Prerequisites
  Java 11 or later
  Intellij idea prefered
  Maven
  MySQL or other relational database
  ## Installation
  1. Switch to backend
     cd backend
  2. Update application properties:
     Configure the database and API key in src/main/resources/application.properties:
      ### MySQL Database configuration
      spring.datasource.url=jdbc:mysql://localhost:3306/weatherdb
      spring.datasource.username=your-username
      spring.datasource.password=your-password

      ### Hibernate configuration
      spring.jpa.hibernate.ddl-auto=update

      ### OpenWeather API key
      openweather.api.key=your-api-key
  3. Install dependencies:
     mvn clean install
  4. Start backend server
     mvn spring-boot:run or if you are using Intillij idea the press Alt+shift+f10
   
