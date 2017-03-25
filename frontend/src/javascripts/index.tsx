/// <reference path="./types/interfaces.d.ts"/>
/// <reference path="../../typings/index.d.ts"/>
require('../stylesheets/index.scss');

import React      from 'react';
import { render } from 'react-dom';
import T          from 'i18n-react';

import Perf from 'react-addons-perf'
(window as any).Perf = Perf

import SolvasRouter from './router.tsx';

T.setTexts(require('../../translations/en.yml'));

render(<SolvasRouter />, document.getElementById('app'));
