import React from "react";
import styles from "./GameTypeForm.module.css";
import { Button } from "@mui/material";

const GameTypeForm = (props) => {
    return (
        <div className={styles["game-type-form"]}>
            <Button
                className={`${styles["computer-vs-player-button"]} ${
                    props.gameType.value === "computer_vs_player" &&
                    `${styles["selected"]}`
                }`}
                onClick={props.gameTypeHandler}
                name="computer_vs_player"
            >
                <img
                    className={styles["game-type-icon"]}
                    src={require("../../../assets/player_vs_computer.png")}
                    alt="computer vs person icon"
                    name="computer_vs_player"
                ></img>
            </Button>
            <Button
                className={`${styles["player-vs-player-button"]} ${
                    props.gameType.value === "player_vs_player" &&
                    `${styles["selected"]}`
                }`}
                onClick={props.gameTypeHandler}
                name="player_vs_player"
            >
                <img
                    className={styles["game-type-icon"]}
                    src={require("../../../assets/player_vs_player.png")}
                    alt="person vs person icon"
                    name="player_vs_player"
                ></img>
            </Button>
        </div>
    );
};

export default GameTypeForm;
