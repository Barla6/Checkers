import React, { useState } from "react";
import { Grid } from "@mui/material";
import Square from "./Square";
import styles from "./Board.module.css";
import { temporaryBoardData } from "./temporaryBoardData";

const Board = () => {
    const [boardData, setBoardData] = useState(temporaryBoardData);
    return (
        <div className={styles["board"]}>
            <Grid className={styles["board-grid"]} container columns={8}>
                {boardData.map((square, index) => (
                    <Grid
                        className={styles["board-grid-item"]}
                        item
                        xs={1}
                        key={index}
                    >
                        <Square
                            colored={square.col % 2 === (square.row + 1) % 2}
                            piece={square.piece}
                        >
                            x
                        </Square>
                    </Grid>
                ))}
            </Grid>
        </div>
    );
};

export default Board;
