import axios from 'axios';

const BASE_URL = 'http://localhost:8080/api/v1';

export const signIn = (email, password) => {
    const endpoint = `${BASE_URL}/sign-in`;
    return axios.post(endpoint, {
        email: email,
        password: password
    });
};
