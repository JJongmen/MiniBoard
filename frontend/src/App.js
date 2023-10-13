// import logo from './logo.svg';
import './App.css';
import { Route, Routes } from 'react-router-dom';
import React from "react";
import SignIn from './routes/SignIn';
import SignUp from './routes/SignUp';
import 'bootstrap/dist/css/bootstrap.min.css';
import Board from './routes/Board';


function App() {
  return (
    <Routes>
      <Route path="/" element={<Board/>} />
      <Route path="/sign-in" element={<SignIn/>} />
      <Route path="/sign-up" element={<SignUp/>} />
    </Routes>
  );
}

export default App;
