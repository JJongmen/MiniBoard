import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import '@fontsource/roboto/300.css';
import '@fontsource/roboto/400.css';
import '@fontsource/roboto/500.css';
import '@fontsource/roboto/700.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import Header from './layout/Header';
import { BrowserRouter } from 'react-router-dom';
import 'bootstrap/dist/css/bootstrap.min.css';
import GlobalSnackbar from './components/GlobalSnackbar'; 
import { Provider } from 'react-redux'; 
import store from './redux/store';
import { AuthProvider } from './auth/AuthContext';

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <AuthProvider>
    <Provider store={store}>
      <BrowserRouter>
        <Header />
        <App />
        <GlobalSnackbar />
      </BrowserRouter>
    </Provider>
  </AuthProvider>
);

reportWebVitals();