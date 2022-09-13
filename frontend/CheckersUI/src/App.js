import "./App.css";
import StartPage from "./components/StartPage/StartPage";
import { Route, Switch } from "react-router-dom";
import GamePage from "./components/GamePage/GamePage";
import GameContext from "./context/game-context";

function App() {
    return (
        <div className="App">
            <GameContext.Provider>
                <Switch>
                    <Route path="/" exact>
                        <StartPage />
                    </Route>
                    <Route path="/game" exact>
                        <GamePage />
                    </Route>
                </Switch>
            </GameContext.Provider>
        </div>
    );
}

export default App;
