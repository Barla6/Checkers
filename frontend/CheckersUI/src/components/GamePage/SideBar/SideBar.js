import React from "react";
import styles from "./SideBar.module.css";
import GameLevel from "./sideBarComponents/GameLevel";
import QuitButton from "./sideBarComponents/QuitButton";

const SideBar = (props) => {
    return (
        <div className={styles["sidebar"]}>
            {props.showGameLevel && <GameLevel />}
            {props.showQuitButton && <QuitButton />}
        </div>
    );
};

export default SideBar;
