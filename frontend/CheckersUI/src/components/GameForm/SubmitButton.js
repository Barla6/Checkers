import React from "react";
import { Button } from "@mui/material";
import styles from "./SubmitButton.module.css";

const SubmitButton = (props) => {
    return (
        <Button
            className={styles["submit-button"]}
            onClick={props.submitHandler}
        >
            Play
        </Button>
    );
};

export default SubmitButton;
