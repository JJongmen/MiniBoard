import axios from 'axios';

const BASE_URL = process.env.REACT_APP_API_BASE_URL;

export const getPostDetail = async (postId) => {
  try {
    const response = await axios.get(`${BASE_URL}/posts/${postId}`);
    return response.data;
  } catch (error) {
    throw error.response ? error.response.data : error;
  }
};
