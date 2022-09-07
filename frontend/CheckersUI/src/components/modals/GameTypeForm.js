import React from "react";
import ReactDOM from "react-dom";
import styles from "./GameTypeForm.module.css";
import { Card, CardContent, CardActions, Button } from "@mui/material";

const GameTypeFormModal = (props) => {
    return (
        <React.Fragment>
            <div className={styles.backdrop}></div>
            <Card className={styles["game-type-card"]}>
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
                <CardContent className={styles["game-type-card-content"]}>
                    <Button
                        className={styles["computer-vs-player-button"]}
                        onClick={props.chooseGameTypeHanlder}
                        name="computer vs player button"
                    >
                        <img
                            className={styles["game-type-icon"]}
                            src={require("../../assets/player_vs_computer.png")}
                            alt="computer vs person icon"
                            name="computer vs player button"
                        ></img>
                    </Button>
                    <Button
                        disabled={true}
                        className={styles["player-vs-player-button"]}
                        onClick={props.chooseGameTypeHanlder}
                        name="player vs player button"
                    >
                        <img
                            className={styles["game-type-icon"]}
                            src={require("../../assets/player_vs_player.png")}
                            alt="person vs person icon"
                            name="player vs player button"
                        ></img>
                    </Button>
                </CardContent>
            </Card>
        </React.Fragment>
    );
};

const GameTypeForm = (props) => {
    return (
        <React.Fragment>
            {props.open &&
                ReactDOM.createPortal(
                    <GameTypeFormModal
                        exitHandler={props.exitHandler}
                        chooseGameTypeHanlder={props.chooseGameTypeHanlder}
                    ></GameTypeFormModal>,
                    document.getElementById("overlay-root")
                )}
        </React.Fragment>
    );
};

export default GameTypeForm;
