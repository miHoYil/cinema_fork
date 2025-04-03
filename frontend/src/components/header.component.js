import React from "react";
import { Link, useNavigate } from "react-router-dom";
import AuthService from "../services/auth.service";

const Header = () => {
    const navigate = useNavigate();

    const handleLogout = () => {
        AuthService.logout();
        navigate("/signin");
    };

    return (
        <header style={styles.header}>
            <nav style={styles.nav}>
                <Link to="/signin" style={styles.button}>Sign In</Link>
                <Link to="/signup" style={styles.button}>Sign Up</Link>
                <Link to="/movie" style={styles.button}>Movie</Link>
                <button onClick={handleLogout} style={styles.button}>Logout</button>
            </nav>
        </header>
    );
};

const styles = {
    header: {
        backgroundColor: "#333",
        padding: "10px",
        display: "flex",
        justifyContent: "center",
    },
    nav: {
        display: "flex",
        gap: "15px",
    },
    button: {
        color: "#fff",
        textDecoration: "none",
        backgroundColor: "#555",
        padding: "8px 16px",
        borderRadius: "5px",
        border: "none",
        cursor: "pointer",
    },
};

export default Header;