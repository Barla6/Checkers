import React, { useContext, useState } from "react";
import { Grid } from "@mui/material";
import Square from "./Square";
import styles from "./Board.module.css";
import GameContext from "../../../context/game-context";
import { api } from "../../../API";

const Board = async () => {
    const boardContext = useContext(GameContext);
    const board = [
        {
            coordinates: {
                row: 0,
                col: 0,
            },
        },
        {
            coordinates: {
                row: 0,
                col: 1,
            },
            piece: {
                playerId: "medium",
                type: "REGULAR",
            },
        },
        {
            coordinates: {
                row: 0,
                col: 2,
            },
        },
        {
            coordinates: {
                row: 0,
                col: 3,
            },
            piece: {
                playerId: "medium",
                type: "REGULAR",
            },
        },
        {
            coordinates: {
                row: 0,
                col: 4,
            },
        },
        {
            coordinates: {
                row: 0,
                col: 5,
            },
            piece: {
                playerId: "medium",
                type: "REGULAR",
            },
        },
        {
            coordinates: {
                row: 0,
                col: 6,
            },
        },
        {
            coordinates: {
                row: 0,
                col: 7,
            },
            piece: {
                playerId: "medium",
                type: "REGULAR",
            },
        },
        {
            coordinates: {
                row: 1,
                col: 0,
            },
            piece: {
                playerId: "medium",
                type: "REGULAR",
            },
        },
        {
            coordinates: {
                row: 1,
                col: 1,
            },
        },
        {
            coordinates: {
                row: 1,
                col: 2,
            },
            piece: {
                playerId: "medium",
                type: "REGULAR",
            },
        },
        {
            coordinates: {
                row: 1,
                col: 3,
            },
        },
        {
            coordinates: {
                row: 1,
                col: 4,
            },
            piece: {
                playerId: "medium",
                type: "REGULAR",
            },
        },
        {
            coordinates: {
                row: 1,
                col: 5,
            },
        },
        {
            coordinates: {
                row: 1,
                col: 6,
            },
            piece: {
                playerId: "medium",
                type: "REGULAR",
            },
        },
        {
            coordinates: {
                row: 1,
                col: 7,
            },
        },
        {
            coordinates: {
                row: 2,
                col: 0,
            },
        },
        {
            coordinates: {
                row: 2,
                col: 1,
            },
            piece: {
                playerId: "medium",
                type: "REGULAR",
            },
        },
        {
            coordinates: {
                row: 2,
                col: 2,
            },
        },
        {
            coordinates: {
                row: 2,
                col: 3,
            },
            piece: {
                playerId: "medium",
                type: "REGULAR",
            },
        },
        {
            coordinates: {
                row: 2,
                col: 4,
            },
        },
        {
            coordinates: {
                row: 2,
                col: 5,
            },
            piece: {
                playerId: "medium",
                type: "REGULAR",
            },
        },
        {
            coordinates: {
                row: 2,
                col: 6,
            },
        },
        {
            coordinates: {
                row: 2,
                col: 7,
            },
            piece: {
                playerId: "medium",
                type: "REGULAR",
            },
        },
        {
            coordinates: {
                row: 3,
                col: 0,
            },
        },
        {
            coordinates: {
                row: 3,
                col: 1,
            },
        },
        {
            coordinates: {
                row: 3,
                col: 2,
            },
        },
        {
            coordinates: {
                row: 3,
                col: 3,
            },
        },
        {
            coordinates: {
                row: 3,
                col: 4,
            },
        },
        {
            coordinates: {
                row: 3,
                col: 5,
            },
        },
        {
            coordinates: {
                row: 3,
                col: 6,
            },
        },
        {
            coordinates: {
                row: 3,
                col: 7,
            },
        },
        {
            coordinates: {
                row: 4,
                col: 0,
            },
        },
        {
            coordinates: {
                row: 4,
                col: 1,
            },
        },
        {
            coordinates: {
                row: 4,
                col: 2,
            },
        },
        {
            coordinates: {
                row: 4,
                col: 3,
            },
        },
        {
            coordinates: {
                row: 4,
                col: 4,
            },
        },
        {
            coordinates: {
                row: 4,
                col: 5,
            },
        },
        {
            coordinates: {
                row: 4,
                col: 6,
            },
        },
        {
            coordinates: {
                row: 4,
                col: 7,
            },
        },
        {
            coordinates: {
                row: 5,
                col: 0,
            },
            piece: {
                playerId: "0",
                type: "REGULAR",
            },
        },
        {
            coordinates: {
                row: 5,
                col: 1,
            },
        },
        {
            coordinates: {
                row: 5,
                col: 2,
            },
            piece: {
                playerId: "0",
                type: "REGULAR",
            },
        },
        {
            coordinates: {
                row: 5,
                col: 3,
            },
        },
        {
            coordinates: {
                row: 5,
                col: 4,
            },
            piece: {
                playerId: "0",
                type: "REGULAR",
            },
        },
        {
            coordinates: {
                row: 5,
                col: 5,
            },
        },
        {
            coordinates: {
                row: 5,
                col: 6,
            },
            piece: {
                playerId: "0",
                type: "REGULAR",
            },
        },
        {
            coordinates: {
                row: 5,
                col: 7,
            },
        },
        {
            coordinates: {
                row: 6,
                col: 0,
            },
        },
        {
            coordinates: {
                row: 6,
                col: 1,
            },
            piece: {
                playerId: "0",
                type: "REGULAR",
            },
        },
        {
            coordinates: {
                row: 6,
                col: 2,
            },
        },
        {
            coordinates: {
                row: 6,
                col: 3,
            },
            piece: {
                playerId: "0",
                type: "REGULAR",
            },
        },
        {
            coordinates: {
                row: 6,
                col: 4,
            },
        },
        {
            coordinates: {
                row: 6,
                col: 5,
            },
            piece: {
                playerId: "0",
                type: "REGULAR",
            },
        },
        {
            coordinates: {
                row: 6,
                col: 6,
            },
        },
        {
            coordinates: {
                row: 6,
                col: 7,
            },
            piece: {
                playerId: "0",
                type: "REGULAR",
            },
        },
        {
            coordinates: {
                row: 7,
                col: 0,
            },
            piece: {
                playerId: "0",
                type: "REGULAR",
            },
        },
        {
            coordinates: {
                row: 7,
                col: 1,
            },
        },
        {
            coordinates: {
                row: 7,
                col: 2,
            },
            piece: {
                playerId: "0",
                type: "REGULAR",
            },
        },
        {
            coordinates: {
                row: 7,
                col: 3,
            },
        },
        {
            coordinates: {
                row: 7,
                col: 4,
            },
            piece: {
                playerId: "0",
                type: "REGULAR",
            },
        },
        {
            coordinates: {
                row: 7,
                col: 5,
            },
        },
        {
            coordinates: {
                row: 7,
                col: 6,
            },
            piece: {
                playerId: "0",
                type: "REGULAR",
            },
        },
        {
            coordinates: {
                row: 7,
                col: 7,
            },
        },
    ];
    const [possibleMoves, setPossibleMoves] = useState([]);

    const getPossibleMoves = async (coordinates) => {
        const gameId = localStorage.getItem("gameId");
        const possibleMoves = await api.getPossibleMoves(gameId, coordinates);
        setPossibleMoves(possibleMoves);
    };

    const cleanPossibleMoves = () => {
        setPossibleMoves([]);
    };

    const isPossibleMove = (coordinates) => {
        return (
            possibleMoves.find(
                (move) =>
                    move.row === coordinates.row && move.col === coordinates.col
            ) !== undefined
        );
    };
    return (
        <div className={styles["board"]}>
            <Grid className={styles["board-grid"]} container columns={8}>
                {board.map((square, index) => (
                    <Grid
                        className={styles["board-grid-item"]}
                        item
                        xs={1}
                        key={index}
                    >
                        <Square
                            colored={
                                square.coordinates.col % 2 ===
                                (square.coordinates.row + 1) % 2
                            }
                            square={square}
                            getPossibleMoves={getPossibleMoves}
                            cleanPossibleMoves={cleanPossibleMoves}
                            possibleMove={isPossibleMove(square.coordinates)}
                        />
                    </Grid>
                ))}
            </Grid>
        </div>
    );
};

export default Board;
