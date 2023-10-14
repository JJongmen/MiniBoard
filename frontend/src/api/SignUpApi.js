import axios from 'axios';

const BASE_URL = 'http://localhost:8080/api/v1';

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