import React from "react";
import styles from "./GameLevelForm.module.css";
import { Button } from "@mui/material";

const GameLevelForm = (props) => {
    return (
        <div className={styles["game-level-form"]}>
            <Button
                disabled={props.disabled}
                name="easy"
                className={
                    props.gameLevel === "easy" && `${styles["selected"]}`
                }
                onClick={props.gameLevelHandler}
            >
                easy
            </Button>
            <Button
                disabled={props.disabled}
                name="medium"
                className={
                    props.gameLevel === "medium" && `${styles["selected"]}`
                }
                onClick={props.gameLevelHandler}
            >
                medium
            </Button>
            <Button
                disabled={props.disabled}
                name="hard"
                className={
                    props.gameLevel === "hard" && `${styles["selected"]}`
                }
                onClick={props.gameLevelHandler}
            >
                hard
            </Button>
        </div>
    );
};

export default GameLevelForm;
