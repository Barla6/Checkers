import React, { useState } from "react";

const GameContext = React.createContext({
    gameBoard: [],
    setBoard: () => {},
});

export const GameBoardProvider = (props) => {
    const [gameBoard, setGameBoard] = useState([]);
    const setBoard = (board) => {
        setGameBoard(board);
    };

    return (
        <GameContext.Provider
            value={{
                gameBoard: gameBoard,
                setBoard: setBoard,
            }}
        >
            {props.children}
        </GameContext.Provider>
    );
};
export default GameContext;
