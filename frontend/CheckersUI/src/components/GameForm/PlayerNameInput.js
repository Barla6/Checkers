import React from "react";
import { TextField } from "@mui/material";
import styles from "./PlayerNameInput.module.css";
import { style } from "@mui/system";

const PlayerNameInput = (props) => {
    return (
        <div className={styles["container"]}>
            <input
                className={styles["name-input"]}
                variant="outlined"
                placeholder="Enter your name"
                onChange={props.playerNameHandler}
                maxLength={20}
            />
        </div>
    );
};

export default PlayerNameInput;
