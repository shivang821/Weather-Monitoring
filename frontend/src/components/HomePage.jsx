import { useEffect, useState } from "react";
import axios from "axios";
import WeatherCard from "./WeatherCard";
import './HomePage.css'; // Custom styles

const HomePage = () => {
  const [weatherData, setWeatherData] = useState([]);
  const saveToLocalStorage = (key, data) => {
    localStorage.setItem(key, JSON.stringify(data));
  };

  const loadFromLocalStorage = (key) => {
    const data = localStorage.getItem(key);
    return data ? JSON.parse(data) : null;
  };
  const fetchData = async () => {
    try {
      let { data } = await axios.get('/api/weather/');
      console.log("Fetched data:", data);
      setWeatherData(data);
      saveToLocalStorage('weatherData', data);  // Save to local storage
    } catch (error) {
      console.error("Error fetching weather data:", error);
    }
  };

  useEffect(() => {
    const initialWeatherData = loadFromLocalStorage('weatherData');
    if (initialWeatherData) {
      setWeatherData(initialWeatherData);
    }

    // Fetch data every 2 minutes (120000 ms)
    fetchData();  
    const intervalId = setInterval(() => {
      fetchData();
    }, 120000);

    return () => clearInterval(intervalId);
  }, []); 

  return (
    <div className="home-page">
        <div className="home-page1" >
            <h1>Weather Monitoring Dashboard</h1>
        </div>
      <div className="card-container ">
        <div className="weather-card home-card-container" >
          <div className="city"><p>City</p></div>
          <div className="temp"><p>Temperatire</p></div>
          <div className="feelsLike"><p>Feels Like</p></div>
          <div className="weatherCondition"><p>Weather</p></div>
        </div>
        {weatherData.length>0&&weatherData.map(data => (
          <WeatherCard key={data.id} city={data.city} weather={data} />
        ))}
      </div>
    </div>
  );
};

export default HomePage;
