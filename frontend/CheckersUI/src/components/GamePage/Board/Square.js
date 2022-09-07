import React from "react";
import Piece from "./Piece";
import styles from "./Square.module.css";

const Square = (props) => {
    return (
        <div
            className={styles["square"]}
            style={{ backgroundColor: props.colored ? "#A0B3C3" : "white" }}
        >
            {props.piece && (
                <Piece
                    className={styles["piece"]}
                    color={
                        props.piece.direction === "UPWARDS" ? "black" : "red"
                    }
                    king={props.piece.type === "KING"}
                ></Piece>
            )}
        </div>
    );
};

export default Square;
