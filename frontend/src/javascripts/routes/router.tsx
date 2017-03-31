import React from 'react';
import { Router, Route, IndexRoute, browserHistory } from 'react-router';
import { useBasename } from 'history';

import Auth from '../modules/Auth.ts';

import App       from '../components/app/App.tsx';

import Login      from '../components/Login.tsx';

import EnsureLoggedInContainer from '../components/containers/EnsureLoggedIn.tsx';

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

let f = (_state : any, re : any) => {
  Auth.deauthenticateUser();
  re('/');
}

const client_routes = [
  <Route path="clients"                component={ Clients    } />,
  <Route path="clients/new"            component={ AddClient  } />,
  <Route path="clients/:id"            component={ Client     } />,
  <Route path="clients/:id/edit"       component={ EditClient } />,
  <Route path="clients/:id/fleets"     component={ Fleets     } />
]

const user_routes = [
  <Route path="users/new"      component={ AddUser  } />,
  <Route path="users/:id/edit" component={ EditUser } />,
  <Route path="users"          component={ Users    } >
    <IndexRoute component={ NoUser } />
    <Route path=":id" component={ User } />
  </Route>
]

const vehicles_routes = [
  <Route path="vehicles/new"      component={ AddVehicle  } />,
  <Route path="vehicles/:id/edit" component={ EditVehicle } />,
  <Route path="vehicles"          component={ Vehicles    }>
    <IndexRoute component={ NoVehicle } />
    <Route path=":id" component={ Vehicle } />
  </Route>
]

const fleet_routes = [
  <Route path="fleets/:id" component={ Fleet } />
]

const signed_in_routes = (
  <Route component={ App } >
    <Route path="sign_out" onEnter={ f } />

    { user_routes }
    { client_routes }
    { vehicles_routes }
    { fleet_routes }
  </Route>
);

const SolvasRouter : React.StatelessComponent<{}> = () => {
  const history = useBasename(() => browserHistory)({
    basename: SUB_URI
  });

  return (
    <Router history={ history } >
      <Route path="/">
        <IndexRoute getComponent={ (_s, callback) => { return Auth.isAuthenticated() ? callback(null, App) : callback(null, Login) } } />

        <Route component={ EnsureLoggedInContainer }>
          { signed_in_routes }
        </Route>
        <Route path="*" component={ NoMatch } />
      </Route>
    </Router>
  );
}

export default SolvasRouter;
