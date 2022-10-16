import React from "react";
import styles from "./Piece.module.css";

const Piece = (props) => {
    const myPiece = props.piece.playerId === localStorage.getItem("playerId");
    const king = props.piece.type === "KING";

    const getPieceIcon = () => {
        if (myPiece)
            return king
                ? require("../../../assets/red_king.png")
                : require("../../../assets/red_piece.png");
        else
            return king
                ? require("../../../assets/black_king.png")
                : require("../../../assets/black_piece.png");
    };
    return (
        <img
            className={styles["piece-icon"]}
            src={getPieceIcon()}
            alt="piece icon"
        ></img>
    );
};

export default Piece;
