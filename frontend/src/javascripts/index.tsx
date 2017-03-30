/// <reference path="./types/interfaces.d.ts"/>
/// <reference path="../../typings/index.d.ts"/>
require('../stylesheets/index.scss');

import React      from 'react';
import { render } from 'react-dom';
import T          from 'i18n-react';
import { Provider } from 'react-redux'
import { createStore } from 'redux'
import reducer from './reducers/app.ts';

import Perf from 'react-addons-perf'
(window as any).Perf = Perf

import SolvasRouter from './routes/router.tsx';

T.setTexts(require('../../translations/en.yml'));

let store = createStore(reducer)

render(
  <Provider store={ store }>
    <SolvasRouter />
  </Provider>,
  document.getElementById('app')
);
