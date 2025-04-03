import axios from "axios";
import authHeader from "./auth_header.service";

const API_URL = process.env.REACT_APP_API_URL;
const API_URL_MOVIE = "/api/movies";

const MovieService = {
  createMovie: async (name, timeDuration) => {
    console.log(authHeader());
    return axios.post(`${API_URL}${API_URL_MOVIE}/admin`, {name, timeDuration}, {headers: authHeader()})
        .then(response => {
            console.log("Movie created:", response.data);
            return response.data;
        })
        .catch(error => {
            console.error("Error creating movie:", error);
            throw error;
        });
  },

  getMovies: async () => {
    return axios.get(`${API_URL}${API_URL_MOVIE}`, { headers: authHeader() })
      .then(response => {
        return response.data;
      })
      .catch(error => {
        console.error("Error fetching movies:", error);
        throw error;
      });
  },

  deleteMovie: async (id) => {
    return axios.delete(`${API_URL}${API_URL_MOVIE}/admin/${id}`, { headers: authHeader() });
  }

};

export default MovieService;
