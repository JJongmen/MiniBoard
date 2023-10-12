// import logo from './logo.svg';
import './App.css';
import { Route, Routes } from 'react-router-dom';
import Home from "./routes/Home";
import React from "react";
import BoardList from "./routes/BoardList";
import SignIn from './routes/SignIn';
import SignUp from './routes/SignUp';
import 'bootstrap/dist/css/bootstrap.min.css';


function App() {
  return (
    <Routes>
      <Route path="/" element={<Home/>} />
      <Route path="/board" element={<BoardList/>} />
      <Route path="/sign-in" element={<SignIn/>} />
      <Route path="/sign-up" element={<SignUp/>} />
    </Routes>
  );
}

export default App;
