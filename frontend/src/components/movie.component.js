import React, { useState, useEffect } from "react";
import MovieService from '../services/movie.service';

const Movie = () => {
    const [name, setName] = useState("");
    const [timeDuration, setTimeDuration] = useState("");
    const [error, setError] = useState(null);
    const [movies, setMovies] = useState([]);

    const fetchMovies = async () => {
        try {
            const movieList = await MovieService.getMovies();
            setMovies(movieList);
        } catch (err) {
            console.error("Failed to fetch movies:", err);
        }
    };

    useEffect(() => {
        fetchMovies();
    }, []);

    const handleCreateMovie = async (e) => {
        e.preventDefault();
        try {
            const duration = parseInt(timeDuration, 10) * 60;
            await MovieService.createMovie(name, duration);
            alert("Movie created successfully");
            fetchMovies();

            setName("");
            setTimeDuration("");
        } catch (err) {
            setError("Failed to create movie");
        }
    };

    const handleDeleteMovie = async (id) => {
        try {
            await MovieService.deleteMovie(id);
            fetchMovies();
        } catch (err) {
            console.error("Failed to delete movie:", err);
        }
    };

    function parseDuration(duration) {
        const regex = /^PT(?:(\d+)H)?(?:(\d+)M)?(?:(\d+)S)?$/;
        const matches = duration.match(regex);
      
        if (!matches) {
          return 0;
        }
      
        const hours = matches[1] ? parseInt(matches[1], 10) : 0;
        const minutes = matches[2] ? parseInt(matches[2], 10) : 0;
        const seconds = matches[3] ? parseInt(matches[3], 10) : 0;
      
        return hours * 60 + minutes + seconds / 60;
      }

    return (
        <div>
            <h2>Create Movie</h2>
            <form onSubmit={handleCreateMovie}>
                <div>
                    <label>Name: </label>
                    <input 
                        type="text" 
                        placeholder="Movie Name" 
                        value={name} 
                        onChange={(e) => setName(e.target.value)} 
                        required 
                    />
                </div>
                <div>
                    <label>Duration (in minutes): </label>
                    <input 
                        type="number" 
                        placeholder="Duration" 
                        value={timeDuration} 
                        onChange={(e) => setTimeDuration(e.target.value)} 
                        required
                        min="0"
                    />
                </div>
                <button type="submit">Create Movie</button>
            </form>
            {error && <p>{error}</p>}
            <h3>Movies List</h3>
            <ul>
                {movies.length === 0 ? (
                    <li>No movies available</li>
                ) : (
                    movies.map((movie) => (
                        <li key={movie.id}>
                            <strong>{movie.name}</strong> - {parseDuration(movie.timeDuration)} minutes
                            <button onClick={() => handleDeleteMovie(movie.id)} style={{ marginLeft: "10px", backgroundColor: "grey", color: "white", border: "none", cursor: "pointer" }}>
                                Delete
                            </button>
                        </li>
                    ))
                )}
            </ul>
        </div>
    );
};

export default Movie;
