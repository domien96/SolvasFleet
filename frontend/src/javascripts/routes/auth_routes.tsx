import React from 'react';
import { Route } from 'react-router';

import PermissionControl from '../components/auth/PermissionControl.tsx';
import CreateRole from '../components/auth/role/CreateRole.tsx'

export default [
  { path: 'auth', component: PermissionControl },
  { path: 'auth/roles/new', component: CreateRole }
].map((props, i) => <Route key={ i} { ...props } />);