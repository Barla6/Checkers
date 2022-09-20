import React from "react";
import styles from "./GameLevel.module.css";

const GameLevel = () => {
    return (
        <div className={styles["game-level"]}>
            <p className={styles["title"]}>Game Level:</p>
            <p className={styles["level-name"]}>
                {localStorage.getItem("gameLevel")}
            </p>
        </div>
    );
};

export default GameLevel;
