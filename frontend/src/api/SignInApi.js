import axios from 'axios';

const BASE_URL = process.env.REACT_APP_API_BASE_URL;

export const signIn = (email, password) => {
    const endpoint = `${BASE_URL}/sign-in`;
    return axios.post(endpoint, {
        email: email,
        password: password
    });
};
