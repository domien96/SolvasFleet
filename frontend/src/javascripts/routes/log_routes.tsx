import React from 'react';
import { Route } from 'react-router';

import Log from '../components/log/Log.tsx';
import LogEntry from '../components/log/LogEntry.tsx';

export default [
  <Route key={ 1 } path="log" component={ Log } />,
  <Route key={ 2 } path="log/:id" component={ LogEntry } />,
];
