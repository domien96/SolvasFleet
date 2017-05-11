/// <reference path="./types/interfaces.d.ts"/>
/// <reference path="../../typings/index.d.ts"/>
require('../stylesheets/index.scss');

import React from 'react';
import { render } from 'react-dom';
import { Provider } from 'react-redux';
import { createStore } from 'redux';
import reducer from './reducers/app.ts';
import i18n from './i18n.ts';

import Perf from 'react-addons-perf';
(window as any).Perf = Perf;

import SolvasRouter from './routes/router.tsx';

const store = createStore(reducer);
store.subscribe(i18n(store));

render(
  <Provider store={ store }>
    <SolvasRouter />
  </Provider>,
  document.getElementById('app'),
);
