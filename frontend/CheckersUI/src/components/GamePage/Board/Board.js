import React, { useEffect, useState } from "react";
import { Grid } from "@mui/material";
import Square from "./Square";
import styles from "./Board.module.css";
import { api } from "../../../API";
import ErrorMessage from "../ErrorMessage";
import { useHistory } from "react-router-dom";

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
    const [showError, setShowError] = useState(false);
    const history = useHistory();

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
    const squareClickHandler = async (coordinates) => {
        const possibleStep = getPossibleStep(coordinates);
        const piece = getPiece(board, coordinates);
        if (isChosenCoordinates(coordinates) && turnData.progress.length >= 2) {
            const newBoard = await api.playTurn(turnData.progress);
            if (!newBoard) {
                setShowError(true);
            } else {
                setBoard(newBoard);
                boardData.board = newBoard;
                localStorage.setItem("boardData", JSON.stringify(boardData));
                setChosenPieceCoordinates({});
                setTurnData({
                    progress: [],
                    eaten: false,
                    completed: false,
                });
            }
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

    const getPossibleMoves = async () => {
        const fetchedPossibleMoves = await api.getPossibleMoves(
            board,
            chosenPieceCoordinates,
            turnData.eaten
        );
        if (!fetchedPossibleMoves) {
            setShowError(true);
        } else {
            setPossibleSteps(fetchedPossibleMoves);
        }
    };

    useEffect(() => {
        if (
            chosenPieceCoordinates.row != null &&
            chosenPieceCoordinates.col != null &&
            getPiece(board, chosenPieceCoordinates) &&
            !turnData.completed
        ) {
            getPossibleMoves();
        } else {
            setPossibleSteps([]);
        }
    }, [chosenPieceCoordinates, turnData, board]);

    const goHomeHandler = () => {
        history.push("/");
    };

    return (
        <div className={styles["board"]}>
            <ErrorMessage open={showError} goHomeHandler={goHomeHandler} />
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
