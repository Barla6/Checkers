import React from "react";
import styles from "./Piece.module.css";

const Piece = (props) => {
    const pieceClickHandler = async () => {
        if (props.playerId === localStorage.getItem("playerId")) {
            await props.getPossibleMoves(props.coordinates);
        }
    };

    const pieceBlurHandler = () => {
        console.log("blur");

        props.cleanPossibleMoves();
    };

    const pickPiece = () => {
        if (props.playerId === localStorage.getItem("playerId"))
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
            onClick={pieceClickHandler}
            onBlur={pieceBlurHandler}
            className={styles["piece-icon"]}
            src={pickPiece()}
            alt="piece icon"
        ></img>
    );
};

export default Piece;
