import React, { useEffect, useState } from "react";
import { Grid } from "@mui/material";
import Square from "./Square";
import styles from "./Board.module.css";
import { api } from "../../../API";

const equalCoordinates = (a, b) => {
    return a.row === b.row && a.col === b.col;
};

const Board = () => {
    const boardData = JSON.parse(localStorage.getItem("boardData"));

    const [board] = useState(boardData.board);
    const [chosenPieceCoordinates, setChosenPieceCoordinates] = useState({});
    const [possibleMoves, setPossibleMoves] = useState([]);

    const squareClickHandler = (coordinates) => {
        const currentPiece = board[coordinates.row][coordinates.col];
        if (
            currentPiece != null &&
            currentPiece.playerId === localStorage.getItem("playerId")
        ) {
            setChosenPieceCoordinates(coordinates);
        } else {
            setChosenPieceCoordinates({});
        }
    };

    // useEffect(() => {
    //     const getPossibleMoves = async (coordinates) => {
    //         const fetchedPossibleMoves = await api.getPossibleMoves(
    //             coordinates
    //         );
    //         setPossibleMoves(fetchedPossibleMoves);
    //     };
    //     if (chosenPieceCoordinates.row && chosenPieceCoordinates.col) {
    //         getPossibleMoves(chosenPieceCoordinates);
    //     } else {
    //         setPossibleMoves([]);
    //     }
    // }, [chosenPieceCoordinates]);

    return (
        <div className={styles["board"]}>
            <Grid className={styles["board-grid"]} container columns={8}>
                {[...Array(8).keys()]
                    .map((rowIndex) => {
                        return [...Array(8).keys()].map((colIndex) => {
                            const coordinates = {
                                row: boardData.turnBoard
                                    ? 7 - rowIndex
                                    : rowIndex,
                                col: boardData.turnBoard
                                    ? 7 - colIndex
                                    : colIndex,
                            };
                            return (
                                <Grid
                                    className={styles["board-grid-item"]}
                                    item
                                    xs={1}
                                    key={`${coordinates.row}${coordinates.col}`}
                                >
                                    <Square
                                        square={{
                                            coordinates,
                                            piece: board[coordinates.row][
                                                coordinates.col
                                            ],
                                        }}
                                        chosenPiece={equalCoordinates(
                                            chosenPieceCoordinates,
                                            coordinates
                                        )}
                                        squareClickHandler={squareClickHandler}
                                    >
                                        x
                                    </Square>
                                </Grid>
                            );
                        });
                    })
                    .flat()}
            </Grid>
        </div>
    );
};

export default Board;
