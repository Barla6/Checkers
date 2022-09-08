const axios = require("axios");

const serverURL = "http://127.0.0.1:8080";
const headers = {
    "Content-Type": "application/json",
    "Access-Control-Allow-Origin": "*",
};

export const createNewGame = async (level) => {
    axios
        .get(`${serverURL}/new-game/${level}`, { headers })
        .then((response) => {
            console.log(response.data);
        })
        .catch((err) => {
            console.log(err);
        });
};
