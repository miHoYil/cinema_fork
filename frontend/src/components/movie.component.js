import React, { useState, useEffect } from "react";
import MovieService from '../services/movie.service';

const Movie = () => {
    const [name, setName] = useState("");
    const [timeDuration, setTimeDuration] = useState("");
    const [error, setError] = useState(null);
    const [movies, setMovies] = useState([]);

    const [editingMovie, setEditingMovie] = useState(null);
    const [editName, setEditName] = useState("");
    const [editDuration, setEditDuration] = useState("");

    const openEditDialog = (movie) => {
        setEditingMovie(movie);
        setEditName(movie.name);
        setEditDuration(parseDuration(movie.timeDuration));
    };

    const handleUpdateMovie = async () => {
        try {
            const updatedDuration = parseInt(editDuration, 10) * 60;
            await MovieService.updateMovie(editingMovie.id, editName, updatedDuration);
            setEditingMovie(null);
            fetchMovies();
        } catch (err) {
            console.error("Failed to update movie:", err);
        }
    };

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
            {editingMovie && (
                <div style={{
                    position: "fixed", top: 0, left: 0, right: 0, bottom: 0,
                    backgroundColor: "rgba(0, 0, 0, 0.5)", display: "flex",
                    alignItems: "center", justifyContent: "center"
                }}>
                    <div style={{ background: "white", padding: "20px", borderRadius: "8px" }}>
                        <h3>Edit Movie</h3>
                        <div>
                            <label>Name:</label>
                            <input
                                type="text"
                                value={editName}
                                onChange={(e) => setEditName(e.target.value)}
                                required
                            />
                        </div>
                        <div>
                            <label>Duration (in minutes):</label>
                            <input
                                type="number"
                                min="0"
                                value={editDuration}
                                onChange={(e) => setEditDuration(e.target.value)}
                                required
                            />
                        </div>
                        <button onClick={handleUpdateMovie}>Save</button>
                        <button onClick={() => setEditingMovie(null)} style={{ marginLeft: "10px" }}>Cancel</button>
                    </div>
                </div>
            )}
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
                            <button onClick={() => openEditDialog(movie)} style={{ marginLeft: "10px", backgroundColor: "blue", color: "white", border: "none", cursor: "pointer" }}>
                                Update
                            </button>
                        </li>
                    ))
                )}
            </ul>
        </div>
    );
};

export default Movie;
