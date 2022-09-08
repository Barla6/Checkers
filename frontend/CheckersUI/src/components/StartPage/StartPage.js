import React from "react";
import styles from "./StartPage.module.css";
import { Button } from "@mui/material";
import GameTypeForm from "../modals/GameTypeForm";
import { useState } from "react";
import GameLevelForm from "../modals/GameLevelForm";
import { useHistory } from "react-router-dom";
import { createNewGame } from "../../API";
import GameForm from "../modals/GameForm";

const StartPage = () => {
    const [gameFormOpen, setGameFormOpen] = useState(false);

    const playHandler = () => {
        setGameFormOpen(true);
    };

    const exitGameFormHandler = () => {
        setGameFormOpen(false);
    };

    const chooseGameTypeHanlder = (event) => {
        if (event.target.name === "computer vs player button") {
        } else if (event.target.name === "player vs player button") {
        }
    };

    const gameFormSubmitHandler = (event) => {};

    return (
        <div>
            <GameForm
                open={gameFormOpen}
                exitHandler={exitGameFormHandler}
                submitHandler={gameFormSubmitHandler}
            ></GameForm>
            <div className={styles["start-page"]}>
                <img
                    className={styles["header-image"]}
                    src={require("../../assets/header.png")}
                    alt="header img"
                ></img>
                {/* <h1 className={styles["title"]}>CHECKERS</h1> */}
                <Button
                    className={styles["play-button"]}
                    variant="contained"
                    onClick={playHandler}
                >
                    Play
                </Button>
            </div>
        </div>
    );
};

export default StartPage;
