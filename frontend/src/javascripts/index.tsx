require('../stylesheets/index.scss');

import React from 'react';
import { render } from 'react-dom';
import T from 'i18n-react';

T.setTexts(require('../../translations/en.yml'));

//import Login from './components/Login.tsx';
//import AddCompany from './components/AddCompany.tsx'
//import AddVehicle from './components/AddVehicle.tsx'
import AddUser from './components/AddUser.tsx'

//render(<Login url = 'localhost:8000'/>, document.getElementById('app'));
//render(<AddCompany url = 'localhost:8000'/>, document.getElementById('app'));
//render(<AddVehicle url = 'localhost:8000'/>, document.getElementById('app'));
render(<AddUser url = 'localhost:8000'/>, document.getElementById('app'));
