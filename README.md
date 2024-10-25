This project is a weather monitoring system that fetches real-time weather data from the OpenWeather API and stores it in a database.
The frontend provides a user interface to display weather data and summaries for various cities, while the backend handles API requests, data fetching, and storage.

# clone the repsitory
  git clone https://github.com/shivang821/Weather-Monitoring.git

  cd Weather-Monitoring
  
# Frontend Setup
  Prerequisites:
  
  Node.js
  
  npm or yarn
  
  ## Installation
  1. Switch to frontend:
     
     cd frontend
     
  3. Install dependencies:
     
     npm install
     
     or
     
     yarn install
     
  5. Start the frontend server after starting backend server :
     
     npm run dev
     
     or
     
     yarn dev
     
     The frontend will run on http://localhost:3000.

# Backend Setup

  Prerequisites:
  
   Java 22 (copy the your system java versioin in pom.xml. This project has java 22)
  
   Intellij idea prefered
  
   Maven
  
   MySQL or other relational database
  
  ## Installation
  
  1. Switch to backend (first move to parent directory)
     
     cd backend
     
  2. Update application properties:
     
      spring.application.name=WeatherMonitoringBackend
     
      spring.datasource.url=jdbc:mysql://localhost:3306/weatherdb
     
      spring.datasource.username=username
     
      spring.datasource.password=your_password
      
      spring.jpa.hibernate.ddl-auto=update
     
      spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect
     
      #spring.jpa.show-sql=true
     
      openweather.api.key=your_openweather_api_key

  2. Install dependencies:
     
     mvn clean install
     
  4. Start backend server
     
     mvn spring-boot:run
     
# Database Setup
Please ensure that a MySQL database named `weatherdb` is already created on your system before running the application. The application will automatically create the required tables in this database. You can create the database using the following MySQL command:

```sql
CREATE DATABASE weatherdb;
```
## Note: To ensure accurate and up-to-date weather data, the Weather Monitoring application requires the server to be running continuously (24/7). This allows the application to collect data at specified intervals and update the temperature summary, including minimum, maximum, and average temperatures for each city. If the server is not running continuously, the displayed data in the frontend may not reflect recent updates, resulting in outdated information. It is crucial that the server remains active to maintain data accuracy and consistency.
   
