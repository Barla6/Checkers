import React, { useEffect, useState } from "react";
import { Grid } from "@mui/material";
import Square from "./Square";
import styles from "./Board.module.css";
import { api } from "../../../API";

const equalCoordinates = (a, b) => {
    return a.row === b.row && a.col === b.col;
};

const getPiece = (board, coordinates) => {
    return board[coordinates.row][coordinates.col];
};

const Board = () => {
    const boardData = JSON.parse(localStorage.getItem("boardData"));

    const [board, setBoard] = useState(boardData.board);
    const [chosenPieceCoordinates, setChosenPieceCoordinates] = useState({
        col: null,
        row: null,
    });
    const [turnData, setTurnData] = useState({
        progress: [],
        eaten: false,
        completed: false,
    });
    const [possibleSteps, setPossibleSteps] = useState([]);

    const isChosenCoordinates = (coordinates) => {
        return equalCoordinates(chosenPieceCoordinates, coordinates);
    };

    const isMyPiece = (piece) => {
        return (
            piece != null && piece.playerId === localStorage.getItem("playerId")
        );
    };

    const getPossibleStep = (coordinates) => {
        return possibleSteps.find((step) =>
            equalCoordinates(coordinates, step.step)
        );
    };
    const squareClickHandler = (coordinates) => {
        const possibleStep = getPossibleStep(coordinates);
        const piece = getPiece(board, coordinates);
        if (isChosenCoordinates(coordinates)) {
            // todo: confirm turn, set default values and get the turn of the computer
        } else if (isMyPiece(piece)) {
            setChosenPieceCoordinates(coordinates);
            setBoard(boardData.board);
            setTurnData({
                progress: [coordinates],
                eaten: false,
                completed: false,
            });
        } else if (possibleStep) {
            setChosenPieceCoordinates(possibleStep.step);
            setBoard(possibleStep.board);
            setTurnData((prevState) => {
                const lastCoordinatesInProgress =
                    prevState.progress[prevState.progress.length - 1];
                if (!equalCoordinates(lastCoordinatesInProgress, coordinates)) {
                    prevState.progress.push(coordinates);
                }
                return {
                    eaten: possibleStep.eaten,
                    completed: possibleStep.completed,
                    progress: prevState.progress,
                };
            });
        } else {
            setChosenPieceCoordinates({});
            setBoard(boardData.board);
            setTurnData({
                progress: [],
                eaten: false,
                completed: false,
            });
        }
    };

    useEffect(() => {
        const getPossibleMoves = async (coordinates) => {
            const fetchedPossibleMoves = await api.getPossibleMoves(
                board,
                coordinates,
                turnData.eaten
            );
            setPossibleSteps(fetchedPossibleMoves);
        };
        if (
            chosenPieceCoordinates.row &&
            chosenPieceCoordinates.col &&
            board[chosenPieceCoordinates.row][chosenPieceCoordinates.col] &&
            !turnData.completed
        ) {
            getPossibleMoves(chosenPieceCoordinates);
        } else {
            setPossibleSteps([]);
        }
    }, [chosenPieceCoordinates, turnData, board]);

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
                                        possibleStep={getPossibleStep(
                                            coordinates
                                        )}
                                    />
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
