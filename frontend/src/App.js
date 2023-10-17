// import logo from './logo.svg';
import './App.css';
import { Route, Routes } from 'react-router-dom';
import React from "react";
import SignIn from './routes/SignIn';
import SignUp from './routes/SignUp';
import 'bootstrap/dist/css/bootstrap.min.css';
import Board from './routes/Board';
import Post from './routes/Post';
import PostWrite from './routes/PostWrite';

function App() {
  return (
    <Routes>
      <Route path="/" element={<Board/>} />
      <Route path="/sign-in" element={<SignIn/>} />
      <Route path="/sign-up" element={<SignUp/>} />
      <Route path="/posts/:postId" element={<Post/>} />
      <Route path="/posts/write" element={<PostWrite/>} />
    </Routes>
  );
}

export default App;
