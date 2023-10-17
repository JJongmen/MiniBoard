import axios from 'axios';

const BASE_URL = process.env.REACT_APP_API_BASE_URL;

export const deletePost = async (postId) => {
  try {
    const token = localStorage.getItem('access_token');
    const headers = {
      'Authorization': `Bearer ${token}`
    };

    const response = await axios.delete(`${BASE_URL}/posts/${postId}`, { headers });
    
    return response.data;
  } catch (error) {
    throw error.response ? error.response.data : error;
  }
};
