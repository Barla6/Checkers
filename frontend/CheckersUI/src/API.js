const axios = require("axios");

const serverURL = "http://127.0.0.1:8080";
const headers = {
    "Content-Type": "application/json",
    "Access-Control-Allow-Origin": "*",
};

export const api = {
    createNewGame: async (level, playerName) => {
        return axios
            .post(`${serverURL}/new-game`, { level, playerName }, { headers })
            .then((response) => {
                return response.data;
            })
            .catch((err) => {
                console.log(err);
            });
    },
    getPossibleMoves: async (coordinates) => {
        const gameId = localStorage.getItem("gameId");
        return axios
            .post(
                `${serverURL}/possible-moves`,
                { gameId, coordinates },
                { headers }
            )
            .then((response) => {
                return response.data;
            })
            .catch((err) => {
                console.log(err);
            });
    },
};
