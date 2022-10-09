import React, { useState } from "react";
import { Grid } from "@mui/material";
import Square from "./Square";
import styles from "./Board.module.css";
import { api } from "../../../API";

const Board = () => {
    const getBoard = () => {
        const fetchedBoardData = JSON.parse(localStorage.getItem("boardData"));
        return fetchedBoardData.turnBoard
            ? fetchedBoardData.board.reverse()
            : fetchedBoardData.board;
    };

    const [board] = useState(getBoard());
    const [possibleMoves, setPossibleMoves] = useState([]);
    const [chosenPiece, setChosenPiece] = useState({});

    const getPossibleMoves = async (coordinates) => {
        const response = await api.getPossibleMoves(coordinates);
        setChosenPiece(coordinates);
        setPossibleMoves(response);
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
                            square={square}
                            possibleMove={
                                possibleMoves.filter(
                                    (move) =>
                                        move.row === square.coordinates.row &&
                                        move.col === square.coordinates.col
                                ).length > 0
                            }
                            chosenPiece={
                                chosenPiece.row === square.coordinates.row &&
                                chosenPiece.col === square.coordinates.col
                            }
                            getPossibleMoves={getPossibleMoves}
                        >
                            x
                        </Square>
                    </Grid>
                ))}
            </Grid>
        </div>
    );
};

export default Board;
