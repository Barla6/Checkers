import React, { useReducer } from "react";
import ReactDOM from "react-dom";
import styles from "./GameForm.module.css";
import { Card, CardContent, CardActions } from "@mui/material";
import GameTypeForm from "./GameTypeForm";
import GameLevelForm from "./GameLevelForm";
import ExitButton from "./ExitButton";
import PlayerNameInput from "./PlayerNameInput";
import SubmitButton from "./SubmitButton";
import { useHistory } from "react-router-dom";
import {
    gameLevelReducer,
    gameTypeReducer,
    playerNameReducer,
} from "./gameFormReducers";

const GameFormModal = (props) => {
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

    const submitHandler = (event) => {
        if (validateFormContent()) {
            // Create New Game (http)
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
