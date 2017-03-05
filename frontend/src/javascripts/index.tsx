/// <reference path="./types/interfaces.d.ts"/>
require('../stylesheets/index.scss');

import React      from 'react';
import { render } from 'react-dom';
import T          from 'i18n-react';

T.setTexts(require('../../translations/en.yml'));

import AddRole from './components/AddRole.tsx';

render(<AddRole url = 'localhost:8000'/>, document.getElementById('app'));
