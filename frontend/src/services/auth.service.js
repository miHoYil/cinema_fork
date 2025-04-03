import axios from "axios";

const API_URL = process.env.REACT_APP_API_URL;
const API_URL_AUTH = "/api/auth";

const AuthService = {
  signup: async (username, password) => {
    return axios.post(`${API_URL}${API_URL_AUTH}/signup`, { username, password });
  },
  signin: async (username, password) => {
    return axios.post(`${API_URL}${API_URL_AUTH}/signin`, { username, password })
        .then(response => {
            if (response.data) {
                localStorage.setItem("accessToken", response.data);
                console.log(localStorage.getItem("accessToken"))
            }

            return response.data;
        });
  },
  logout() {
    localStorage.removeItem("accessToken");
  }
};

export default AuthService;