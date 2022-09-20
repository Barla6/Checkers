import "./App.css";
import StartPage from "./components/StartPage/StartPage";
import { Route, Switch } from "react-router-dom";
import GamePage from "./components/GamePage/GamePage";

function App() {
    return (
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
    );
}

export default App;
