import React from 'react';
import { Router, Route, IndexRoute, browserHistory } from 'react-router';
import { useBasename } from 'history';

import Auth from '../modules/Auth.ts';

import App       from '../components/app/App.tsx';
import EnsureLoggedInContainer from '../components/containers/EnsureLoggedIn.tsx';
import EnsureLoggedOutContainer from '../components/containers/EnsureLoggedOut.tsx';

import Login      from '../components/Login.tsx';

import Users    from '../components/users/Users.tsx';
import AddUser  from '../components/user_form/AddUser.tsx';
import EditUser from '../components/user_form/EditUser.tsx';
import User     from '../components/user/User.tsx';
import NoUser   from '../components/user/NoUser.tsx';

import Clients    from '../components/clients/Clients.tsx';
import AddClient  from '../components/client_form/AddClient.tsx';
import EditClient from '../components/client_form/EditClient.tsx';
import Client     from '../components/client/Client.tsx';

import Vehicles    from '../components/vehicles/Vehicles.tsx';
import Vehicle     from '../components/vehicle/Vehicle.tsx';
import NoVehicle   from '../components/vehicle/NoVehicle.tsx';
import AddVehicle  from '../components/vehicle_form/AddVehicle.tsx';
import EditVehicle from '../components/vehicle_form/EditVehicle.tsx';

import Fleets from '../components/fleets/Fleets.tsx';
import Fleet  from '../components/fleet/Fleet.tsx';

import NoMatch   from '../components/NoMatch.tsx';

declare var ENVIRONMENT: string;
declare var SUB_URI: string;

if (ENVIRONMENT == "development") {
  var SUB_URI = "";
} else {
  var SUB_URI = "/app";
}

export function redirect_to(path : string) : void {
  let sep = path[0] == '/' ? '' : '/';
  browserHistory.push(`${SUB_URI}${sep}${path}`);
}

const SolvasRouter : React.StatelessComponent<{}> = () => {
  const history = useBasename(() => browserHistory)({
    basename: SUB_URI
  });

  let f = (_state : any, re : any) => {
    Auth.deauthenticateUser();
    re('/');
  }

  return (
    <Router history={ history } >
      <Route path="/" component={ EnsureLoggedOutContainer }>
        <IndexRoute component={ Login } />
      </Route>
      <Route component={ EnsureLoggedInContainer }>
        <Route path="/" component={ App } >
          <Route path="sign_out" onEnter={ f } />
          <Route path="users/new"      component={ AddUser  } />
          <Route path="users/:id/edit" component={ EditUser } />
          <Route path="users"          component={ Users    } >
            <IndexRoute component={ NoUser } />
            <Route path=":id" component={ User } />
          </Route>

          <Route path="clients"                component={ Clients    } />
          <Route path="clients/new"            component={ AddClient  } />
          <Route path="clients/:id"            component={ Client     } />
          <Route path="clients/:id/edit"       component={ EditClient } />
          <Route path="clients/:id/fleets"     component={ Fleets     } />

          <Route path="vehicles/new"      component={ AddVehicle  } />
          <Route path="vehicles/:id/edit" component={ EditVehicle } />
          <Route path="vehicles"          component={ Vehicles    }>
            <IndexRoute component={ NoVehicle } />
            <Route path=":id" component={ Vehicle } />
          </Route>

          <Route path="fleets/:id" component={ Fleet } />
        </Route>
        <Route path="*" component={ NoMatch } />
      </Route>
    </Router>
  );
}

export default SolvasRouter;
