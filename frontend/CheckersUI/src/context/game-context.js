import React, { useState } from "react";

const GameContext = React.createContext({
    gameLevel: "",
    updateGameContext: (gameProperties) => {},
});

export const GameContextProvider = (props) => {
    const [gameLevel, setGameLevel] = useState("");

    const updateGameContext = (gameProperties) => {
        setGameLevel(gameProperties.gameLevel);
    };

    return (
        <GameContext.Provider
            value={{
                gameLevel: gameLevel,
                updateGameContext: updateGameContext,
            }}
        >
            {props.children.props.children}
        </GameContext.Provider>
    );
};
export default GameContext;
