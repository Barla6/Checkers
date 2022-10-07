import React from "react";
import Piece from "./Piece";
import PossibleMoveMark from "./PossibleMoveMark";
import styles from "./Square.module.css";

const Square = (props) => {
    return (
        <div
            className={styles["square"]}
            style={{ backgroundColor: props.colored ? "#A0B3C3" : "white" }}
        >
            {props.square.piece && (
                <Piece
                    className={styles["piece"]}
                    playerId={props.square.piece.playerId}
                    king={props.square.piece.type === "KING"}
                    getPossibleMoves={props.getPossibleMoves}
                    cleanPossibleMoves={props.cleanPossibleMoves}
                    coordinates={props.square.coordinates}
                ></Piece>
            )}
            {props.possibleMove && <PossibleMoveMark />}
        </div>
    );
};

export default Square;
