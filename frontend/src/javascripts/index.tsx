/// <reference path="./types/interfaces.d.ts"/>
require('../stylesheets/index.scss');

import React      from 'react';
import { render } from 'react-dom';
import T          from 'i18n-react';

T.setTexts(require('../../translations/en.yml'));

import Companies from './components/Companies.tsx';

render(<Companies url = 'http://localhost:8000/sample_data/companies.json'/>, document.getElementById('app'));
