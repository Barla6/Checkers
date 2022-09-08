import React, { useState } from "react";
import ReactDOM from "react-dom";
import styles from "./GameForm.module.css";
import { Card, CardContent, CardActions, Button } from "@mui/material";

const GameFormModal = (props) => {
    const [gameType, setGameType] = useState();

    const gameTypeHandler = (event) => {
        setGameType(event.target.name);
    };

    return (
        <React.Fragment>
            <div className={styles.backdrop}></div>
            <Card className={styles["game-form-card"]}>
                <div className={styles["inner-card"]}>
                    <CardActions className={styles["actions-container"]}>
                        <Button
                            className={styles["exit-button"]}
                            onClick={props.exitHandler}
                        >
                            <img
                                className={styles["exit-icon"]}
                                src={require("../../assets/exit_icon.png")}
                                alt="exit icon"
                            ></img>
                        </Button>
                    </CardActions>
                    <CardContent className={styles["game-form-card-content"]}>
                        <Button
                            className={`${
                                styles["computer-vs-player-button"]
                            } ${
                                gameType === "computer_vs_player" &&
                                `${styles["selected"]}`
                            }`}
                            onClick={gameTypeHandler}
                            name="computer_vs_player"
                        >
                            <img
                                className={styles["game-type-icon"]}
                                src={require("../../assets/player_vs_computer.png")}
                                alt="computer vs person icon"
                                name="computer_vs_player"
                            ></img>
                        </Button>
                        <Button
                            disabled={true}
                            className={`${styles["player-vs-player-button"]} ${
                                gameType === "player_vs_player" &&
                                `${styles["selected"]}`
                            }`}
                            onClick={gameTypeHandler}
                            name="player_vs_player"
                        >
                            <img
                                className={styles["game-type-icon"]}
                                src={require("../../assets/player_vs_player.png")}
                                alt="person vs person icon"
                                name="player_vs_player"
                            ></img>
                        </Button>
                    </CardContent>
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
