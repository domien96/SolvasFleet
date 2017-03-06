import React from 'react';
import { Router, Route, IndexRoute, browserHistory } from 'react-router'

import Companies from './components/Companies.tsx';
import App       from './components/app/App.tsx';
import Home      from './components/Home.tsx';
import NoMatch   from './components/NoMatch.tsx';

class SolvasRouter extends React.Component<{}, {}> {
  render() {
    return (
      <Router history={ browserHistory } >
        <Route path="/" component={ App } >
          <IndexRoute component={ Home } />
          <Route path="companies" component={ Companies } />
          <Route path="*" component={ NoMatch }/>
        </Route>
      </Router>
    );
  }
}

export default SolvasRouter;
