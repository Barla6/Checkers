import React from "react";
import ReactDOM from "react-dom";
import styles from "./GameLevelForm.module.css";
import { Card, CardContent, CardActions, Button } from "@mui/material";
import { Link } from "react-router-dom";

const GameLevelFormModal = (props) => {
    return (
        <React.Fragment>
            <div className={styles.backdrop}></div>
            <Card className={styles["game-level-card"]}>
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
                <CardContent className={styles["game-level-card-content"]}>
                    <Button
                        className={styles["level-easy"]}
                        onClick={props.chooseGameLevelHandler}
                    >
                        easy
                    </Button>
                    <Button
                        className={styles["level-medium"]}
                        onClick={props.chooseGameLevelHandler}
                    >
                        medium
                    </Button>
                    <Button
                        className={styles["level-hard"]}
                        onClick={props.chooseGameLevelHandler}
                    >
                        hard
                    </Button>
                </CardContent>
            </Card>
        </React.Fragment>
    );
};

const GameLevelForm = (props) => {
    return (
        <div>
            {props.open &&
                ReactDOM.createPortal(
                    <GameLevelFormModal
                        exitHandler={props.exitHandler}
                        chooseGameLevelHandler={props.chooseGameLevelHandler}
                    ></GameLevelFormModal>,
                    document.getElementById("overlay-root")
                )}
        </div>
    );
};

export default GameLevelForm;
