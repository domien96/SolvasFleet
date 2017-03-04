require('../stylesheets/index.scss');

import React from 'react';
import { render } from 'react-dom';
import T from 'i18n-react';

T.setTexts({
  submit: "Login"
});

import Login from './components/Login.tsx';

render(<Login url = 'localhost:8000'/>, document.getElementById('app'));
