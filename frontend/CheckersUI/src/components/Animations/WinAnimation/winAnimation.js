import React from "react";
import styles from "./winAnimation.module.css";
import ReactDOM from "react-dom";
import { Button } from "@mui/material";
import { useHistory } from "react-router-dom";
import TextAnimation from "../TextAnimation/TextAnimation";

const WinAnimationComponent = () => {
    const history = useHistory();

    const exitClickHandler = () => {
        history.push("/");
    };
    return (
        <React.Fragment>
            <div className={styles.backdrop}></div>
            <div className={styles["text"]}>
                <div className={styles["big-text"]}>WINNER</div>
            </div>
            <Button
                className={styles["exit-button"]}
                onClick={exitClickHandler}
            >
                Exit
            </Button>
            <lord-icon
                className={styles["confetti"]}
                src="https://cdn.lordicon.com/tqywkdcz.json"
                trigger="loop"
                colors="primary:#4bb3fd,secondary:#f28ba8,tertiary:#ffc738,quaternary:#f24c00"
                style={{
                    width: "30vw",
                    height: "30vw",
                    "z-index": "100",
                    position: "fixed",
                    top: "65%",
                    left: "15%",
                    transform: "translate(-50%, -50%)",
                }}
            ></lord-icon>
            <lord-icon
                className={styles["confetti"]}
                src="https://cdn.lordicon.com/tqywkdcz.json"
                trigger="loop"
                colors="primary:#4bb3fd,secondary:#f28ba8,tertiary:#ffc738,quaternary:#f24c00"
                style={{
                    width: "30vw",
                    height: "30vw",
                    "z-index": "100",
                    position: "fixed",
                    top: "65%",
                    left: "85%",
                    transform: "translate(-50%, -50%) scaleX(-1)",
                }}
            ></lord-icon>
        </React.Fragment>
    );
};

const WinAnimation = (props) => {
    return (
        <React.Fragment>
            {props.open &&
                ReactDOM.createPortal(
                    <WinAnimationComponent />,
                    document.getElementById("loading-root")
                )}
        </React.Fragment>
    );
};

export default WinAnimation;
