import axios from 'axios';

const BASE_URL = process.env.REACT_APP_API_BASE_URL;

export const signUp = async (name, email, password) => {
  try {
    const response = await axios.post(`${BASE_URL}/sign-up`, {
      name,
      email,
      password
    });
    return response.data;
  } catch (error) {
    throw error.response ? error.response.data : error;
  }
};