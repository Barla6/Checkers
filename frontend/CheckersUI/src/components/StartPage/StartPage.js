import React from "react";
import styles from "./StartPage.module.css";
import { Button } from "@mui/material";
import { useState } from "react";
import GameForm from "../GameForm/GameForm";

const StartPage = () => {
    const [gameFormOpen, setGameFormOpen] = useState(false);

    const playHandler = () => {
        setGameFormOpen(true);
    };

    const exitGameFormHandler = () => {
        setGameFormOpen(false);
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
