// import React from "react";
import { useNavigate } from "react-router-dom";
import './WeatherCard.css'; // Custom styles for glassmorphism

const WeatherCard = ({ city, weather }) => {
  const navigate = useNavigate();

  const handleClick = () => {
    navigate(`/summary/${city}`);
  };

  if (!weather) return null;

//   const backgroundImage = weather.main === "Rain" ? "/rainy.jpg" : "/clear.jpg";
//   style={{ backgroundImage: `url(${backgroundImage})` }}

  return (
    <div className="weather-card" onClick={handleClick} >
      <div className="city"><p>{city}</p></div>
      <div className="temp">{Math.round(weather.temperature)}°C</div>
      <div className="feelsLike">{Math.round(weather.feelsLike)}°C</div>
      <div className="weatherCondition">{weather.weatherCondition}</div>
      {/* <div className="weather-card1">
      <p>{Math.round(weather.temperature)} / {Math.round(weather.feelsLike)}°C</p>
      
      </div>
      <div>
        <p> </p>
        <p>{weather.weatherCondition}</p>
      </div> */}
    </div>
  );
};

export default WeatherCard;
