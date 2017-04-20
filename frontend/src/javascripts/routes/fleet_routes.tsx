import React from 'react';
import { Route } from 'react-router';

import Fleet from '../components/fleet/Fleet.tsx';
import Invoices  from '../components/invoices/Invoices.tsx'
import Invoice  from '../components/invoices/Invoice.tsx'

export default [
  <Route path="fleets/:id" component={ Fleet } />,
  <Route path="fleets/:fleetId/invoices" component={ Invoices } />,
  <Route path="fleets/:fleetId/invoices/:invoiceId" component={ Invoice } />
]
