import React from 'react';
import { Route } from 'react-router';

import Commissions from '../components/commissions/Commissions.tsx';

export default [
  { path: 'commissions', component: Commissions },
].map((props, i) => <Route key={ i} { ...props } />);
