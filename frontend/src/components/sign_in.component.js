import React, { useState } from "react";
import AuthService from '../services/auth.service';

const Signin = () => {
    const [login, setLogin] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState(null);
  
    const handleSignin = async (e) => {
      e.preventDefault();
      try {
        await AuthService.signin(login, password);
        alert("Login successful");
      } catch (err) {
        setError("Login failed");
      }
    };
  
    return (
      <div>
        <h2>Sign In</h2>
        <form onSubmit={handleSignin}>
          <input type="login" placeholder="Login" value={login} onChange={(e) => setLogin(e.target.value)} required />
          <input type="password" placeholder="Password" value={password} onChange={(e) => setPassword(e.target.value)} required />
          <button type="submit">Login</button>
        </form>
        {error && <p>{error}</p>}
      </div>
    );
  };

export default Signin;