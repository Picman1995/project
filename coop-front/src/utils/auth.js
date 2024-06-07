import axios from 'axios';

const LOGIN_ENDPOINT = 'auth/login';

export const loginUser = async (username, password) => {
  const response = await axios.post(LOGIN_ENDPOINT, { username, password });
  return response;
};
