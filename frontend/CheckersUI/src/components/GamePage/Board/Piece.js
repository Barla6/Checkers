import React from "react";
import styles from "./Piece.module.css";

const Piece = (props) => {
    const myPiece = props.piece.playerId === localStorage.getItem("playerId");
    const king = props.piece.type === "KING";
    const pieceClickHandler = async () => {
        if (myPiece) {
            await props.getPossibleMoves(props.coordinates);
        }
    };

    const pickPiece = () => {
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
            src={pickPiece()}
            alt="piece icon"
            onClick={pieceClickHandler}
        ></img>
    );
};

export default Piece;
