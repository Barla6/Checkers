import React from "react";
import styles from "./Piece.module.css";

const Piece = (props) => {
    const pickPiece = () => {
        if (props.color === "red")
            return props.king
                ? require("../../../assets/red_king.png")
                : require("../../../assets/red_piece.png");
        else
            return props.king
                ? require("../../../assets/black_king.png")
                : require("../../../assets/black_piece.png");
    };
    return (
        <img
            className={styles["piece-icon"]}
            src={pickPiece()}
            alt="piece icon"
        ></img>
    );
};

export default Piece;
