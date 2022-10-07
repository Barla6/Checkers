import "./App.css";
import StartPage from "./components/StartPage/StartPage";
import { Route, Switch } from "react-router-dom";
import GamePage from "./components/GamePage/GamePage";
import { GameBoardProvider } from "./context/game-context";

function App() {
    return (
        <GameBoardProvider>
            <div className="App">
                <Switch>
                    <Route path="/" exact>
                        <StartPage />
                    </Route>
                    <Route path="/game" exact>
                        <GamePage />
                    </Route>
                </Switch>
            </div>
        </GameBoardProvider>
    );
}

export default App;
