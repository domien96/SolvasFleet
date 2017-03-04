require('../stylesheets/index.scss');

import React from 'react';
import { render } from 'react-dom';

//import Login from './components/Login.tsx';
import AddCompany from './components/AddCompany.tsx'

//render(<Login url = 'localhost:8000'/>, document.getElementById('app'));
render(<AddCompany url = 'localhost:8000'/>, document.getElementById('app'));
