import React from 'react';
import { Route } from 'react-router';

import Fleet from '../components/fleet/Fleet.tsx';
import Invoices from '../components/invoices/Invoices.tsx';
import Invoice from '../components/invoices/Invoice.tsx';

export default [
  <Route key={ 1 } path="fleets/:id" component={ Fleet } />,
  <Route key={ 2 } path="fleets/:fleetId/invoices" component={ Invoices } />,
  <Route key={ 3 } path="fleets/:fleetId/invoices/:invoiceId" component={ Invoice } />,
];
