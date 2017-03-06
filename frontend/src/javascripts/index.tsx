/// <reference path="./types/interfaces.d.ts"/>
require('../stylesheets/index.scss');

import React      from 'react';
import { render } from 'react-dom';
import T          from 'i18n-react';

import SolvasRouter from './router.tsx';

T.setTexts(require('../../translations/en.yml'));

render(<SolvasRouter />, document.getElementById('app'));
