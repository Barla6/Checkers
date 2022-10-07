import React from "react";
import Board from "./Board/Board";
import SideBar from "./SideBar/SideBar";
import styles from "./GamePage.module.css";

const GamePage = () => {
    return (
        <div className={styles["game-page"]}>
            <SideBar className={styles["sidebar"]}></SideBar>
            <Board className={styles["board"]}></Board>
            <SideBar
                className={styles["sidebar"]}
                showGameLevel={true}
                showQuitButton={true}
            ></SideBar>
        </div>
    );
};

export default GamePage;
