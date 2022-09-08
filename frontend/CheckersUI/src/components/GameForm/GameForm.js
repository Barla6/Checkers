import React, { useState } from "react";
import ReactDOM from "react-dom";
import styles from "./GameForm.module.css";
import { Card, CardContent, CardActions, Button } from "@mui/material";
import GameTypeForm from "./GameTypeForm";
import GameLevelForm from "./GameLevelForm";
import ExitButton from "./ExitButton";
import PlayerNameInput from "./PlayerNameInput";
import SubmitButton from "./SubmitButton";

const GameFormModal = (props) => {
    const [gameType, setGameType] = useState();
    const [gameLevel, setGameLevel] = useState();
    const [playerName, setPlayerName] = useState();

    const gameTypeHandler = (event) => {
        const chosenGameType = event.target.name;
        if (chosenGameType === "player_vs_player") {
            setGameLevel(null);
        }
        setGameType(chosenGameType);
    };

    const gameLevelHandler = (event) => {
        setGameLevel(event.target.name);
    };

    const playerNameHandler = (event) => {
        setPlayerName(event.target.value);
    };

    const submitHandler = (event) => {
        if (isNameValid()) {
            // Create New Game
            // move to game page
            // close form
            props.exitHandler();
        }
    };

    const isNameValid = () => {
        // check if it is unique
        return true;
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
                            disabled={gameType === "player_vs_player"}
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
