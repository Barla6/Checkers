import React from "react";
import styles from "./PlayerNameInput.module.css";

const PlayerNameInput = (props) => {
    return (
        <div className={styles["container"]}>
            <input
                className={styles["name-input"]}
                variant="outlined"
                placeholder="Nickname"
                onChange={props.playerNameHandler}
                maxLength={20}
            />
        </div>
    );
};

export default PlayerNameInput;
