const getBaseUrl = () => {  
    const { hostname } = window.location;
    return `http://${hostname}:7999`;
};
  
const config = {
    API_URL: getBaseUrl(),
};
  
export default config;
  