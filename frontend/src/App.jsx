import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import HomePage from "./components/HomePage";
import CityWeatherSummary from "./components/CityWeatherSummary";

function App() {
  return (
    <div className="app">
      <Router>
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/summary/:city" element={<CityWeatherSummary />} />
        </Routes>
      </Router>
    </div>
  );
}

export default App;
