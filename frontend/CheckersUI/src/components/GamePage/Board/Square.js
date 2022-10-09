import React from "react";
import Piece from "./Piece";
import PossibleMoveMark from "./PossibleMoveMark";
import styles from "./Square.module.css";

const Square = (props) => {
    const colored =
        props.square.coordinates.col % 2 ===
        (props.square.coordinates.row + 1) % 2;

    return (
        <div
            className={`${styles["square"]} ${
                props.chosenPiece && `${styles["chosen-piece"]}`
            } ${props.possibleMove && `${styles["possible-move"]}`}`}
            style={{ backgroundColor: colored ? "#A0B3C3" : "white" }}
        >
            {props.square.piece && (
                <Piece
                    className={styles["piece"]}
                    piece={props.square.piece}
                    coordinates={props.square.coordinates}
                    getPossibleMoves={props.getPossibleMoves}
                ></Piece>
            )}
            {props.possibleMove && <PossibleMoveMark />}
        </div>
    );
};

export default Square;
