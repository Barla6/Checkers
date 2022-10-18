import React from "react";
import ReactDOM from "react-dom";
import { Card, CardActions, CardContent, Button } from "@mui/material";
import styles from "./ErrorMessage.module.css";
import { useHistory } from "react-router-dom";

const ErrorMessageModal = (props) => {
    const history = useHistory();

    const goHomeHandler = () => {
        history.push("/");
    };

    return (
        <React.Fragment>
            <div className={styles.backdrop}></div>
            <Card className={styles["error-message-card"]}>
                <div className={styles["inner-card"]}>
                    <CardContent className={styles["content"]}>
                        <div className={styles["text"]}>
                            Server is not responding...
                        </div>
                    </CardContent>
                    <CardActions className={styles["actions"]}>
                        <Button
                            className={styles["go-home-button"]}
                            onClick={goHomeHandler}
                        >
                            Exit
                        </Button>
                    </CardActions>
                </div>
            </Card>
        </React.Fragment>
    );
};

const ErrorMessage = (props) => {
    return (
        <React.Fragment>
            {props.open &&
                ReactDOM.createPortal(
                    <ErrorMessageModal />,
                    document.getElementById("overlay-root")
                )}
        </React.Fragment>
    );
};

export default ErrorMessage;
