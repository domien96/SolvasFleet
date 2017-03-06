/// <reference path="./types/interfaces.d.ts"/>
require('../stylesheets/index.scss');

import React      from 'react';
import { render } from 'react-dom';
import { Router, Route, IndexRoute, browserHistory } from 'react-router'
import T          from 'i18n-react';

T.setTexts(require('../../translations/en.yml'));

import Companies from './components/Companies.tsx';
import App       from './components/app/App.tsx';
import Home      from './components/Home.tsx';
import NoMatch   from './components/NoMatch.tsx';

render((
  <Router history={ browserHistory } >
    <Route path="/" component={ App } >
      <IndexRoute component={ Home } />
      <Route path="companies" component={ Companies } />
      <Route path="*" component={ NoMatch }/>
    </Route>
  </Router>
), document.getElementById('app'));
