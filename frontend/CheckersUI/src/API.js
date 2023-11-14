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
                console.log(response.data)
                return response.data;
            })
            .catch((err) => {
                console.log(err);
            });
    },
    getPossibleMoves: async (board, coordinates, eaten, turnProgress) => {
        return axios
            .post(
                `${serverURL}/possible-moves`,
                { board, coordinates, eaten, turnProgress },
                { headers }
            )
            .then((response) => {
                console.log(response.data)
                return response.data;
            })
            .catch((err) => {
                console.log(err);
            });
    },
    playTurn: async (turnProgress) => {
        const gameId = Number(localStorage.getItem("gameId"));
        const playerId = Number(localStorage.getItem("playerId"));
        return axios
            .post(
                `${serverURL}/play-turn`,
                { turnProgress, gameId, playerId },
                { headers }
            ).then((response) => {
                return response.data
            })
            .catch((err) => {
                console.log(err);
            });
    },
    getTurn: async  () => {
        const gameId = Number(localStorage.getItem("gameId"));
        const playerId = Number(localStorage.getItem("aiPlayerId"));
        return axios
            .post(`${serverURL}/get-turn`,
                { gameId, playerId },
                { headers }
            )
            .then((response) => {
                console.log(response.data)
                return response.data;
            })
            .catch((err) => {
                console.log(err);
            })
    }
};
