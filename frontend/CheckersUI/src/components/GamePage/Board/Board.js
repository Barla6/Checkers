import React, { useEffect, useState, useReducer } from "react";
import { Grid } from "@mui/material";
import Square from "./Square";
import styles from "./Board.module.css";
import { api } from "../../../API";
import ErrorMessage from "../ErrorMessage";
import WinAnimation from "../../Animations/WinAnimation/winAnimation";
import LostAnimation from "../../Animations/LostAnimation/lostAnimation";

const equalCoordinates = (a, b) => {
    return a.row === b.row && a.col === b.col;
};

const getPiece = (board, coordinates) => {
    return board[coordinates.row][coordinates.col];
};

const gameDataReducer = (state, action) => {
    switch (action.type) {
        case "PLAY_TURN":
            return {
                board: action.value.board,
                chosenPieceCoordinates: { row: null, col: null },
                turnData: {
                    progress: [],
                    eaten: false,
                    completed: false,
                },
            };
        case "CHOOSE_PIECE":
            return {
                board: action.value.board,
                chosenPieceCoordinates: action.value.chosenPieceCoordinates,
                turnData: {
                    progress: [action.value.chosenPieceCoordinates],
                    eaten: false,
                    completed: false,
                },
            };

        case "STEP":
            const lastCoordinatesInProgress =
                state.turnData.progress[state.turnData.progress.length - 1];
            if (
                !equalCoordinates(
                    lastCoordinatesInProgress,
                    action.value.possibleStep.step
                )
            ) {
                state.turnData.progress.push(action.value.possibleStep.step);
            }
            return {
                board: action.value.possibleStep.board,
                chosenPieceCoordinates: action.value.possibleStep.step,
                turnData: {
                    progress: state.turnData.progress,
                    eaten: action.value.possibleStep.eaten,
                    completed: action.value.possibleStep.completed,
                },
            };
        default:
            return {
                board: action.value.board,
                chosenPieceCoordinates: { row: null, col: null },
                turnData: {
                    progress: [],
                    eaten: false,
                    completed: false,
                },
            };
    }
};

const Board = () => {
    const boardData = JSON.parse(localStorage.getItem("boardData"));

    const [gameData, dispatchGameData] = useReducer(gameDataReducer, {
        board: boardData.board,
        chosenPieceCoordinates: { row: null, col: null },
        turnData: {
            progress: [],
            eaten: false,
            completed: false,
        },
    });
    const [possibleSteps, setPossibleSteps] = useState([]);
    const [showError, setShowError] = useState(false);
    const [winner, setWinner] = useState(null);

    const isChosenCoordinates = (coordinates) => {
        return equalCoordinates(gameData.chosenPieceCoordinates, coordinates);
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
        const piece = getPiece(gameData.board, coordinates);
        if (
            isChosenCoordinates(coordinates) &&
            gameData.turnData.progress.length >= 2
        ) {
            const response = await api.playTurn(gameData.turnData.progress);
            if (!response) {
                setShowError(true);
            } else {
                dispatchGameData({
                    type: "PLAY_TURN",
                    value: {
                        board: response.board,
                    },
                });
                boardData.board = response.board;
                localStorage.setItem("boardData", JSON.stringify(boardData));
                setWinner(response.winner);
                console.log(winner);
            }
        } else if (isMyPiece(piece)) {
            dispatchGameData({
                type: "CHOOSE_PIECE",
                value: {
                    board: boardData.board,
                    chosenPieceCoordinates: coordinates,
                },
            });
        } else if (possibleStep) {
            dispatchGameData({
                type: "STEP",
                value: {
                    possibleStep,
                },
            });
        } else {
            dispatchGameData({
                type: "DEFAULT",
                value: {
                    board: boardData.board,
                },
            });
        }
    };

    useEffect(() => {
        const getPossibleMoves = async () => {
            const fetchedPossibleMoves = await api.getPossibleMoves(
                gameData.board,
                gameData.chosenPieceCoordinates,
                gameData.turnData.eaten
            );
            if (!fetchedPossibleMoves) {
                setShowError(true);
            } else {
                setPossibleSteps(fetchedPossibleMoves);
            }
        };
        if (
            gameData.chosenPieceCoordinates.row != null &&
            gameData.chosenPieceCoordinates.col != null &&
            getPiece(gameData.board, gameData.chosenPieceCoordinates) &&
            !gameData.turnData.completed
        ) {
            getPossibleMoves();
        } else {
            setPossibleSteps([]);
        }
    }, [gameData]);

    return (
        <div className={styles["board"]}>
            <WinAnimation open={winner === localStorage.getItem("playerId")} />
            <LostAnimation
                open={winner && winner !== localStorage.getItem("playerId")}
            />
            <ErrorMessage open={showError} />
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
                                            piece: gameData.board[
                                                coordinates.row
                                            ][coordinates.col],
                                        }}
                                        chosenPiece={equalCoordinates(
                                            gameData.chosenPieceCoordinates,
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
