import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import "./CityWeatherSummary.css";

const CityWeatherSummary = () => {
  const { city } = useParams();

  // Get data from localStorage if available or set to null
  const [weatherSummary, setWeatherSummary] = useState(() => {
    const savedSummary = localStorage.getItem(`weatherSummary-${city}`);
    return savedSummary ? JSON.parse(savedSummary) : null;
  });

  const [weatherData, setWeatherData] = useState(() => {
    const savedData = localStorage.getItem(`weatherData-${city}`);
    return savedData ? JSON.parse(savedData) : null;
  });

  const [pastWeatherSummary, setPastWeatherSummary] = useState(() => {
    const savedPastSummary = localStorage.getItem(`pastWeatherSummary-${city}`);
    return savedPastSummary ? JSON.parse(savedPastSummary) : null;
  });

  // Fetch and update both state and localStorage when data is received from backend
  useEffect(() => {
    async function fetchData() {
      try {
        let { data } = await axios.get(`/api/weather/${city}`);
        setWeatherData(data);
        localStorage.setItem(`weatherData-${city}`, JSON.stringify(data)); // Save to localStorage
      } catch (error) {
        console.error("Error fetching weather data", error);
      }
    }

    async function fetchWeatherSummary() {
      try {
        let { data } = await axios.get(`/api/weather/summary/${city}`);
        setWeatherSummary(data);
        
        localStorage.setItem(`weatherSummary-${city}`, JSON.stringify(data)); // Save to localStorage
      } catch (error) {
        console.error("Error fetching weather summary", error);
      }
    }

    async function fetchPastSummary() {
      try {
        let { data } = await axios.get(`/api/weather/past-summary/${city}`);
        setPastWeatherSummary(data);
        localStorage.setItem(`pastWeatherSummary-${city}`, JSON.stringify(data)); // Save to localStorage
      } catch (error) {
        console.error("Error fetching past weather summary", error);
      }
    }

    // Call API functions
    fetchData();
    fetchWeatherSummary();
    fetchPastSummary();
  }, [city]);
  const weekdays = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
  // const { city } = useParams();
  // const [weatherSummary, setWeatherSummary] = useState(null);
  // const [weatherData, setWeatherData] = useState(null);
  // const [pastWeatherSummary, setPastWeatherSummary] = useState(null);

  // useEffect(() => {
  //   async function fetchData() {
  //     let {data}=await axios.get(`/api/weather/${city}`)
  //     setWeatherData(data);
  //     console.log(data);
      
  //   }
  //   async function fetchWeatherSummary() {
  //     let { data } = await axios.get(`/api/weather/summary/${city}`);
  //     setWeatherSummary(data);
  //     console.log(data);
  //   }
  //   async function fetchPastSummary() {
  //     let {data}=await axios.get(`/api/weather/past-summary/${city}`)
  //     setPastWeatherSummary(data);
  //     console.log(data);
  //   }
  //   fetchData();
  //   fetchWeatherSummary();
  //   fetchPastSummary();
  // }, [city]);

//   const backgroundImage =
//     weatherSummary.main === "Rain" ? "/rainy.jpg" : "/clear.jpg";
  console.log(pastWeatherSummary);
  
  return (
    <>
      {pastWeatherSummary!=null&&weatherData!=null? (
        <div className="city-summary">
            <div className="summary1">
              <div className="summary11">
                <div className="summary111">
                  <h1>{Math.round(weatherData.temperature)}°C</h1>
                  <p>Feels like {Math.round(weatherData.feelsLike)}°C</p>
                </div>
              </div>
              <div className="summary12">
              <div className="summary121">
                  <h1>{weatherData.city}</h1>
                </div>
              </div>
            </div>
            <div className="summary2">
                <div className="summary21"><h1>Summary</h1></div>
                <div className="summary22">
                  <div className="summary221">
                    <div>Day</div>
                    <div>Weather</div>
                    <div>Min. Temp.</div>
                    <div>Avg. Temp.</div>
                    <div>Max. Temp.</div>
                  </div>
                  {
                    pastWeatherSummary.map((summary,i)=>{
                      return <>
                      <div className="summary221">
                        <div>{i==0?'Today':weekdays[new Date(summary.date).getDay()]}</div>
                        <div>{summary.weatherCondition}</div>
                        <div>{Math.round(summary.minTemp)}°C</div>
                        <div>{Math.round(summary.avgTemp)}°C</div>
                        <div>{Math.round(summary.maxTemp)}°C</div>
                      </div>
                        </>
                    })
                  }
                </div>
            </div>
           
        </div>
      ):
        <div className="city-summary">
            <h1>Loading...</h1>
        </div>
      }
    </>
  );
};

export default CityWeatherSummary;
