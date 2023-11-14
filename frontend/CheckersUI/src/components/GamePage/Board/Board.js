import React, {useEffect, useState} from "react";
import { Grid } from "@mui/material";
import Square from "./Square";
import styles from "./Board.module.css";
import { api } from "../../../API";
import ErrorMessage from "../ErrorMessage";
import WinAnimation from "../../Animations/WinAnimation/winAnimation";
import LostAnimation from "../../Animations/LostAnimation/lostAnimation";

const Board = () => {

    const boardFromLocalStorage = JSON.parse(localStorage.getItem("board"));


    /************ STATE ************/
    const [board, setBoard] = useState(boardFromLocalStorage);
    const [chosenPieceCoordinates, setChosenPieceCoordinates] = useState({ row: null, col: null });
    const [turnProgress, setTurnProgress] = useState([]);
    const [hasEaten, setHasEaten] = useState(false);
    const [isTurnCompleted, setIsTurnCompleted] = useState(false);
    const [possibleSteps, setPossibleSteps] = useState([]);
    const [showError, setShowError] = useState(false);
    const [winner, setWinner] = useState(null);


    const equalCoordinates = (a, b) => {
        return a.row === b.row && a.col === b.col;
    };

    const isMyPiece = (piece) => {
        return (
            piece != null && piece.playerId === Number(localStorage.getItem("playerId"))
        );
    };

    const getPossibleStep = (coordinates) => {
        return possibleSteps.find((step) =>
            equalCoordinates(coordinates, step.targetCoordinates)
        );
    };

    const getPiece = (coordinates) => {
        return board[coordinates.row][coordinates.col];
    };

    const squareClickHandler = async (coordinates) => {
        if (isTurnCompleted) {
            return
        }
        const possibleStep = getPossibleStep(coordinates);
        const piece = getPiece(coordinates);

        if (isMyPiece(piece)) {
            if (turnProgress.length >= 2) {
                await api.playTurn(turnProgress);
                terminateTurn()
                getOtherPlayerTurn()
            } else {
                await handleChoosingPiece(coordinates)
            }
        } else if (possibleStep) {
            await handleChoosingStep(possibleStep)
        } else {
            setChosenPieceCoordinates({ row: null, col: null });
            setPossibleSteps([]);
            setTurnProgress([]);
            setIsTurnCompleted(false);
            setHasEaten(false);
        }
    };

    useEffect(() => {
        const fetchPossibleMoves = async () => {
            const fetchedPossibleMoves = await api.getPossibleMoves(
                JSON.parse(localStorage.getItem("board")),
                chosenPieceCoordinates,
                hasEaten,
                turnProgress
            );
            if (!fetchedPossibleMoves) {
                setShowError(true);
            } else {
                setPossibleSteps(fetchedPossibleMoves);
            }
        }
        if (chosenPieceCoordinates.row != null && chosenPieceCoordinates.col != null
            && getPiece(chosenPieceCoordinates) && !isTurnCompleted) {
            fetchPossibleMoves()
        } else {
            setPossibleSteps([]);
        }
    }, [chosenPieceCoordinates]);

    const handleChoosingPiece = async (coordinates) => {
        setChosenPieceCoordinates(coordinates)
        setTurnProgress([coordinates])
    }

    const handleChoosingStep = async (chosenStep) => {
        setBoard(chosenStep.board)
        turnProgress.push(chosenStep.targetCoordinates)
        setHasEaten(chosenStep.eaten)

        if (chosenStep.completed) {

            await api.playTurn(turnProgress);
            terminateTurn()
            getOtherPlayerTurn()

        } else {
            setChosenPieceCoordinates(chosenStep.targetCoordinates)
        }
    }

    const terminateTurn = () => {
        setIsTurnCompleted(true)
        setTurnProgress([])
        setChosenPieceCoordinates({ row: null, col: null })
        setHasEaten(false)
    }

    const getOtherPlayerTurn = () => {
        setTimeout(async () => {
            const turnResult = await api.getTurn()
            setBoard(turnResult.board)
            setWinner(turnResult.winner)
            localStorage.setItem("board", JSON.stringify(turnResult.board));
            setIsTurnCompleted(false);
        }, 1000);
    }

    return (
        <div className={styles["board"]}>
            <WinAnimation open={winner === Number(localStorage.getItem("playerId"))} />
            <LostAnimation
                open={winner && winner !== Number(localStorage.getItem("playerId"))}
            />
            <ErrorMessage open={showError} />
            <Grid className={styles["board-grid"]} container columns={8}>
                {[...Array(8).keys()]
                    .map((rowIndex) => {
                        return [...Array(8).keys()].map((colIndex) => {
                            const coordinates = { row: rowIndex, col: colIndex };
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
                                            piece: board[
                                                coordinates.row
                                            ][coordinates.col],
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
                    }).flat()
                }
            </Grid>
        </div>
    );
};

export default Board;
