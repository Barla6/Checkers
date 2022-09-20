import React from "react";
import ReactDOM from "react-dom";
import styles from "./loadingAnimation.module.css";

const LoadingAnimationComponent = (props) => {
    return (
        <React.Fragment>
            <div className={styles["backdrop"]}></div>
            <p className={styles["text"]}>{props.text}</p>
            <svg
                className={styles["loading-animation"]}
                xmlns="http://www.w3.org/2000/svg"
                viewBox="0 0 100 100"
                preserveAspectRatio="xMidYMid"
            >
                <circle
                    cx="50"
                    cy="50"
                    r="32"
                    stroke-width="8"
                    stroke="#e06565"
                    stroke-dasharray="50.26548245743669 50.26548245743669"
                    fill="none"
                    stroke-linecap="round"
                >
                    <animateTransform
                        attributeName="transform"
                        type="rotate"
                        dur="4.166666666666666s"
                        repeatCount="indefinite"
                        keyTimes="0;1"
                        values="0 50 50;360 50 50"
                    ></animateTransform>
                </circle>
                <circle
                    cx="50"
                    cy="50"
                    r="23"
                    stroke-width="8"
                    stroke="#a0b3c3"
                    stroke-dasharray="36.12831551628262 36.12831551628262"
                    stroke-dashoffset="36.12831551628262"
                    fill="none"
                    stroke-linecap="round"
                >
                    <animateTransform
                        attributeName="transform"
                        type="rotate"
                        dur="4.166666666666666s"
                        repeatCount="indefinite"
                        keyTimes="0;1"
                        values="0 50 50;-360 50 50"
                    ></animateTransform>
                </circle>
            </svg>
        </React.Fragment>
    );
};

const LoadingAnimation = (props) => {
    return (
        <React.Fragment>
            {props.show &&
                ReactDOM.createPortal(
                    <LoadingAnimationComponent text={props.text} />,
                    document.getElementById("loading-root")
                )}
        </React.Fragment>
    );
};

export default LoadingAnimation;
