import React from "react";
import Board from "./Board/Board";
import SideBar from "./SideBar/SideBar";
import styles from "./GamePage.module.css";

const GamePage = (props) => {
    return (
        <div className={styles["game-page"]}>
            <SideBar className={styles["sidebar"]}></SideBar>
            <Board className={styles["board"]}></Board>
            <SideBar className={styles["sidebar"]}></SideBar>
        </div>
    );
};

export default GamePage;
