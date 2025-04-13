import axios from "axios";
import authHeader from "./auth_header.service";
import config from '../config';

const API_URL = config.API_URL;
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
  },

  updateMovie: async (id, name, timeDuration) => {
    return axios.put(`${API_URL}${API_URL_MOVIE}/admin/${id}`, { name, timeDuration }, { headers: authHeader() })
        .then(response => response.data)
        .catch(error => {
            console.error("Error updating movie:", error);
            throw error;
        });
  },

};

export default MovieService;
