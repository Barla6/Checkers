import { Button } from "@mui/material";
import React from "react";
import styles from "./QuitButton.module.css";
import { useHistory } from "react-router-dom";

const QuitButton = (props) => {
    const history = useHistory();

    const quitHandler = () => {
        // todo: ARE YOU SURE?
        history.push("/");
    };

    return (
        <Button className={styles["quit-button"]}>
            <img
                className={styles["quit-icon"]}
                alt="quit icon"
                src={require("../../../../assets/quit_icon.png")}
                onClick={quitHandler}
            ></img>
        </Button>
    );
};

export default QuitButton;
