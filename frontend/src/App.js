import logo from './logo.svg';
import './App.css';
import { Route, Routes } from 'react-router-dom';
import Home from "./routes/Home";
import React from "react";
import BoardList from "./routes/BoardList";

function App() {
  return (
    <Routes>
      <Route path="/" element={<Home/>} />
      <Route path="/board" element={<BoardList/>} />
    </Routes>
  );
}

export default App;
