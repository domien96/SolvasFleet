import React from 'react';
import { Route } from 'react-router';

import Fleet from '../components/fleet/Fleet.tsx';

export default [
  <Route path="fleets/:id" component={ Fleet } />
]
