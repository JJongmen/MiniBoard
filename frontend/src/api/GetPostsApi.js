import axios from 'axios';

const BASE_URL = process.env.REACT_APP_API_BASE_URL;

export const getPosts = async (page = 0, size = 10) => {
  try {
    const response = await axios.get(`${BASE_URL}/posts`, {
      params: {
        page,
        size
      }
    });
    
    return response.data;
  } catch (error) {
    throw error.response ? error.response.data : error;
  }
};
