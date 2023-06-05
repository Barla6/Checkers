import React from "react";
import ReactDOM from "react-dom";
import styles from "./lostAnimation.module.css";
import { useHistory } from "react-router-dom";
import { Button } from "@mui/material";

const LostAnimationComponent = () => {
    const history = useHistory();

    const exitClickHandler = () => {
        history.push("/");
    };
    return (
        <React.Fragment>
            <div className={styles.backdrop}></div>
            <div className={styles["top-text"]}>GAME</div>
            <div className={styles["bottom-text"]}>OVER</div>
            <Button
                className={styles["exit-button"]}
                onClick={exitClickHandler}
            >
                Exit
            </Button>
        </React.Fragment>
    );
};

const LostAnimation = (props) => {
    return (
        <React.Fragment>
            {props.open &&
                ReactDOM.createPortal(
                    <LostAnimationComponent />,
                    document.getElementById("loading-root")
                )}
        </React.Fragment>
    );
};

export default LostAnimation;
