import React from 'react';
import { Router, Route, IndexRoute, browserHistory, RouterState, RedirectFunction } from 'react-router';
import { useBasename } from 'history';

import Auth from '../modules/Auth.ts';

import EnsureLoggedInContainer from '../components/containers/EnsureLoggedIn.tsx';

import client_routes from './client_routes.tsx';
import user_routes from './user_routes.tsx';
import vehicle_routes from './vehicle_routes.tsx';
import fleet_routes from './fleet_routes.tsx';
import auth_routes from './auth_routes.tsx';
import contract_routes from './contract_routes.tsx';
import log_routes from './log_routes.tsx';

import NoMatch from '../components/NoMatch.tsx';
import App from '../components/app/App.tsx';
import Login from '../components/Login.tsx';

declare const ENVIRONMENT: string;
const SUB_URI: string = (ENVIRONMENT === 'development' ? '' : '/app');

export function redirect_to(path: string): void {
  const sep = path[0] === '/' ? '' : '/';
  browserHistory.push(`${SUB_URI}${sep}${path}`);
}

const signOutAndRedirect = (_state: RouterState, re: RedirectFunction) => {
  Auth.deauthenticateUser();
  re('/');
};

const SolvasRouter: React.StatelessComponent<{}> = () => {
  const history = useBasename(() => browserHistory)({
    basename: SUB_URI,
  });

  return (
    <Router history={ history } >
      <Route path='/'>
        <IndexRoute
          getComponent={ (_s, callback) => (Auth.isAuthenticated() ? callback(null, App) : callback(null, Login)) } />

        <Route component={ EnsureLoggedInContainer }>
          <Route component={ App } >
            <Route path='sign_out' onEnter={ signOutAndRedirect } />

            { user_routes }
            { client_routes }
            { vehicle_routes }
            { fleet_routes }
            { auth_routes }
            { contract_routes }
            { log_routes }

          </Route>
        </Route>
        <Route path='*' component={ NoMatch } />
      </Route>
    </Router>
  );
};

export default SolvasRouter;
