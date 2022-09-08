import React from "react";
import { Button } from "@mui/material";
import styles from "./ExitButton.module.css";

const ExitButton = (props) => {
    return (
        <Button className={styles["exit-button"]} onClick={props.exitHandler}>
            <img
                className={styles["exit-icon"]}
                src={require("../../assets/exit_icon.png")}
                alt="exit icon"
            ></img>
        </Button>
    );
};

export default ExitButton;
