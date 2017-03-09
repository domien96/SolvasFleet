import React from 'react';
import { Router, Route, IndexRoute, browserHistory } from 'react-router'

import App       from './components/app/App.tsx';

import Home      from './components/Home.tsx';

import Users   from './components/users/Users.tsx';
import AddUser from './components/users/AddUser.tsx';
import User    from './components/users/User.tsx';
import NoUser    from './components/users/NoUser.tsx';

import Clients   from './components/clients/Clients.tsx';
import AddClient from './components/clients/AddClient.tsx';
import Client    from './components/clients/Client.tsx';

import Vehicles    from './components/vehicles/Vehicles.tsx';

import NoMatch   from './components/NoMatch.tsx';

class SolvasRouter extends React.Component<{}, {}> {
  render() {
    return (
      <Router history={ browserHistory } >
        <Route path="/" component={ App } >
          <IndexRoute component={ Home } />
          <Route path="/users/new" component={ AddUser } />
          <Route path="/users"     component={ Users }>
            <IndexRoute component={ NoUser } />
            <Route path="/users/:id" component={ User } />
          </Route>

          <Route path="/clients"     component={ Clients   } />
          <Route path="/clients/new" component={ AddClient } />
          <Route path="/clients/:id" component={ Client    } />

          <Route path="/vehicles"     component={ Vehicles   } />

          <Route path="*" component={ NoMatch }/>
        </Route>
      </Router>
    );
  }
}

export default SolvasRouter;
