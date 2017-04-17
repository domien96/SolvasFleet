import React from 'react';
import { Route } from 'react-router';

import PermissionControl from '../components/auth/PermissionControl.tsx';

export default [
  { path: 'auth', component: PermissionControl }
].map((props, i) => <Route key={ i} { ...props } />);