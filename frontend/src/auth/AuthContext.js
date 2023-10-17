import React, { createContext, useContext, useState } from 'react';

const AuthContext = createContext({ isLoggedIn: false, userName: null });

export const AuthProvider = ({ children }) => {
  const [isLoggedIn, setIsLoggedIn] = useState(!!localStorage.getItem('access_token'));
  const [userName, setUserName] = useState(localStorage.getItem('user_name'));

  const login = (token, name) => {
    localStorage.setItem('access_token', token);
    localStorage.setItem('user_name', name);
    setIsLoggedIn(true);
    setUserName(name);
  };

  const logout = () => {
    localStorage.removeItem('access_token');
    localStorage.removeItem('user_name');
    setIsLoggedIn(false);
    setUserName(null);
  };

  const contextValue = {
    isLoggedIn,
    setIsLoggedIn,
    userName,
    setUserName,
    login,
    logout
  };

  return <AuthContext.Provider value={contextValue}>{children}</AuthContext.Provider>;
};

export const useAuth = () => {
  return useContext(AuthContext);
};