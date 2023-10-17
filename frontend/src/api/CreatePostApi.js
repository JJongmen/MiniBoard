import axios from 'axios';

const BASE_URL = process.env.REACT_APP_API_BASE_URL;

export const createPost = async (title, content) => {
  try {
    const token = localStorage.getItem('access_token');
    
    const headers = {
      'Authorization': `Bearer ${token}`
    };
    
    const response = await axios.post(`${BASE_URL}/posts`, {
      title,
      content
    }, { headers });
    
    return response.data;
  } catch (error) {
    throw error.response ? error.response.data : error;
  }
};