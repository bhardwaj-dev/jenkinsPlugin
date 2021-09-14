import logo from './logo.svg';
import './App.css';
import { Switch, Route,Link } from 'react-router-dom';
import Roster from './Roster.js';
function App() {
  return (
    <div className="App">
      <header className="App-header">
//      <iframe src="http://192.168.29.45:3000/d-solo/mBaf_OGnz/health-care-dashboard?orgId=1&from=1628057872369&to=1628079472370&panelId=2" width="450" height="200" frameBorder="0"></iframe>
//      <iframe src="http://localhost:3000/d/jV0xZQG7k/jenkins-status-00004?orgId=1" width="450" height="200" frameBorder="0"></iframe>
<iframe src="http://localhost:3000/d-solo/jV0xZQG7k/jenkins-status-00004?orgId=1&from=1630585054259&to=1630585354259&panelId=9" width="450" height="200" frameborder="0"></iframe>
      <nav>
      <ul>

        <li>
          <Link to="/roster">Users</Link>
        </li>
      </ul>
    </nav>
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header>
      <Switch>
      <Route path='/roster' component={Roster}/>
      </Switch>
      </div>
  );
}

export default App;
