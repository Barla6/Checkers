export const gameTypeReducer = (state, action) => {
    const validateGameType = (gameType) => {
        return gameType != null;
    };

    switch (action.type) {
        case "CHANGE":
            return {
                value: action.value,
                isValid: validateGameType(action.value),
            };
        default:
            return {
                value: state.value,
                isValid: false,
            };
    }
};

export const gameLevelReducer = (state, action) => {
    const validateGameLevel = (gameLevel, gameType = "computer_vs_player") => {
        if (gameType === "player_vs_player") return true;
        else if (gameLevel != null) return true;
        else return false;
    };

    switch (action.type) {
        case "CHANGE":
            return {
                value: action.value,
                isValid: validateGameLevel(action.value),
            };
        case "PLAYER_VS_PLAYER":
            return {
                value: action.value,
                isValid: validateGameLevel(action.value, "player_vs_player"),
            };
        default:
            return {
                value: "",
                isValid: false,
            };
    }
};

export const playerNameReducer = (state, action) => {
    const validateName = (playerName) => {
        return (
            playerName != null &&
            playerName.length > 0 &&
            playerName.length < 20
        );
    };

    switch (action.type) {
        case "CHANGE":
            return {
                value: action.value,
                isValid: validateName(action.value),
            };
        default:
            return {
                value: "",
                isValid: false,
            };
    }
};
