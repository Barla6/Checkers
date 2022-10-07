import React, { useReducer, useContext } from "react";
import ReactDOM from "react-dom";
import styles from "./GameForm.module.css";
import { Card, CardContent, CardActions } from "@mui/material";
import GameTypeForm from "./gameFormComponents/GameTypeForm";
import GameLevelForm from "./gameFormComponents/GameLevelForm";
import ExitButton from "./gameFormComponents/ExitButton";
import PlayerNameInput from "./gameFormComponents/PlayerNameInput";
import SubmitButton from "./gameFormComponents/SubmitButton";
import { useHistory } from "react-router-dom";
import {
    gameLevelReducer,
    gameTypeReducer,
    playerNameReducer,
} from "./gameFormReducers";
import { api } from "../../API";
import GameContext from "../../context/game-context";

const GameFormModal = (props) => {
    const boardContext = useContext(GameContext);

    const [gameType, dispatchGameType] = useReducer(gameTypeReducer, {
        value: "",
        isValid: false,
    });
    const [gameLevel, dispatchGameLevel] = useReducer(gameLevelReducer, {
        value: "",
        isValid: false,
    });
    const [playerName, dispatchPlayerName] = useReducer(playerNameReducer, "");
    const history = useHistory();

    const gameTypeHandler = (event) => {
        const chosenGameType = event.target.name;
        if (chosenGameType === "player_vs_player") {
            dispatchGameLevel({ type: "PLAYER_VS_PLAYER", value: "" });
        }
        dispatchGameType({ type: "CHANGE", value: chosenGameType });
    };

    const gameLevelHandler = (event) => {
        dispatchGameLevel({ type: "CHANGE", value: event.target.name });
    };

    const playerNameHandler = (event) => {
        dispatchPlayerName({ type: "CHANGE", value: event.target.value });
    };

    const submitHandler = async () => {
        if (validateFormContent()) {
            const response = await api.createNewGame(
                gameLevel.value,
                playerName.value
            );
            boardContext.setBoard(response.board);
            localStorage.setItem("gameId", response.gameId);
            localStorage.setItem("playerId", response.playerId);
            localStorage.setItem("turnBoard", response.turnBoard); // todo: use game board
            localStorage.setItem("gameLevel", gameLevel.value);
            localStorage.setItem("gameType", gameType.value);
            localStorage.setItem("playerName", playerName.value); // todo: maybe delete
            history.push("/game");
            props.exitHandler();
        }
    };

    const validateFormContent = () => {
        return gameType.isValid && gameLevel.isValid && playerName.isValid;
    };

    return (
        <React.Fragment>
            <div className={styles.backdrop}></div>
            <Card className={styles["game-form-card"]}>
                <div className={styles["inner-card"]}>
                    <CardActions className={styles["actions"]}>
                        <ExitButton exitHandler={props.exitHandler} />
                    </CardActions>
                    <CardContent className={styles["content"]}>
                        <PlayerNameInput
                            playerNameHandler={playerNameHandler}
                        />
                        <GameTypeForm
                            gameType={gameType}
                            gameTypeHandler={gameTypeHandler}
                        />
                        <GameLevelForm
                            gameLevel={gameLevel}
                            gameLevelHandler={gameLevelHandler}
                            disabled={gameType.value !== "computer_vs_player"}
                        />
                    </CardContent>
                    <CardActions>
                        <SubmitButton submitHandler={submitHandler} />
                    </CardActions>
                </div>
            </Card>
        </React.Fragment>
    );
};

const GameForm = (props) => {
    return (
        <React.Fragment>
            {props.open &&
                ReactDOM.createPortal(
                    <GameFormModal
                        exitHandler={props.exitHandler}
                        chooseGameTypeHanlder={props.chooseGameTypeHanlder}
                    ></GameFormModal>,
                    document.getElementById("overlay-root")
                )}
        </React.Fragment>
    );
};

export default GameForm;
