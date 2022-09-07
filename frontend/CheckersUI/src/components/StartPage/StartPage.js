import React from "react";
import styles from "./StartPage.module.css";
import { Button } from "@mui/material";
import GameTypeForm from "../modals/GameTypeForm";
import { useState } from "react";
import GameLevelForm from "../modals/GameLevelForm";
import { useHistory } from "react-router-dom";

const StartPage = () => {
    const [gameTypeModalOpen, setGameTypeModalOpen] = useState(false);
    const [gameLevelModalOpen, setGameLevelModalOpen] = useState(false);
    const history = useHistory();

    const playHandler = () => {
        setGameTypeModalOpen(true);
    };

    const exitGameTypeModalHandler = () => {
        setGameTypeModalOpen(false);
    };

    const exitGameLevelModalHandler = () => {
        setGameLevelModalOpen(false);
    };

    const chooseGameTypeHanlder = (event) => {
        if (event.target.name === "computer vs player button") {
            setGameLevelModalOpen(true);
            setGameTypeModalOpen(false);
        } else if (event.target.name === "player vs player button") {
            console.log("pvsp");
        }
    };

    const chooseGameLevelHandler = () => {
        setGameLevelModalOpen(false);
        history.push("/game");
    };

    return (
        <div>
            <GameTypeForm
                open={gameTypeModalOpen}
                exitHandler={exitGameTypeModalHandler}
                chooseGameTypeHanlder={chooseGameTypeHanlder}
            ></GameTypeForm>
            <GameLevelForm
                open={gameLevelModalOpen}
                exitHandler={exitGameLevelModalHandler}
                chooseGameLevelHandler={chooseGameLevelHandler}
            ></GameLevelForm>
            <div className={styles["start-page"]}>
                <header className={styles["header"]}></header>
                <div className={styles["bottom-div"]}>
                    <Button variant="contained" onClick={playHandler}>
                        Play
                    </Button>
                </div>
            </div>
        </div>
    );
};

export default StartPage;
