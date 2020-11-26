import React from 'react';
import LoginPage from './pages/login';
import PrivateRoute from './routes/private';
import { Route, Router, Switch } from 'react-router-dom';
import ClientPage from './pages/client';
import { createBrowserHistory } from 'history';
import CreateClientPage from './pages/create-client';
import { MuiThemeProvider } from '@material-ui/core';

const history = createBrowserHistory();

function App() {
  return (
      <Router history={history}>
        <Switch>
            <Route path="/login" component={LoginPage}/>
            <PrivateRoute exact={true} path="/" component={ClientPage}/>
            <PrivateRoute exact path="/client" component={CreateClientPage}/>
            <PrivateRoute exact path="/client/:id" component={CreateClientPage}/>
        </Switch>
      </Router>
  );
}

export default App;
