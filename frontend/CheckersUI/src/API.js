const axios = require("axios");

const serverURL = "http://127.0.0.1:8080";
const headers = {
    "Content-Type": "application/json",
    "Access-Control-Allow-Origin": "*",
};

export const api = {
    createNewGame: async (level, playerName) => {
        console.log("create new game request was sent");
        return axios
            .post(`${serverURL}/new-game`, { level, playerName }, { headers })
            .then((response) => {
                return response.data;
            })
            .catch((err) => {
                console.log(err);
            });
    },

    getPossibleMoves: async (gameId, coordinates) => {
        console.log("find possible moves request was sent");
        return axios
            .post(
                `${serverURL}/possible-moves`,
                {
                    gameId,
                    coordinates,
                },
                { headers }
            )
            .then((response) => {
                console.log(response.data);
                return response.data;
            })
            .catch((err) => {
                console.log(err);
            });
    },

    getBoard: async (gameId) => {
        console.log("get board request was sent");
        return axios
            .get(`${serverURL}/board/${gameId}`, { headers })
            .then((response) => {
                return response.data;
            })
            .catch((err) => {
                console.log(err);
            });
    },
};
