require('../stylesheets/index.scss');

import React from 'react';
import { render } from 'react-dom';
import T from 'i18n-react';

T.setTexts(require('../../translations/en.yml'));

//import Login from './components/Login.tsx';
import AddCompany from './components/AddCompany.tsx'

//render(<Login url = 'localhost:8000'/>, document.getElementById('app'));
render(<AddCompany url = 'localhost:8000'/>, document.getElementById('app'));
