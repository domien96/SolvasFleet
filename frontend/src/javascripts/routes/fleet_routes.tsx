import React from 'react';
import { Route } from 'react-router';

import Fleet from '../components/fleet/Fleet.tsx';
import Invoices  from '../components/invoices/Invoices.tsx'
import Invoice  from '../components/invoices/Invoice.tsx'

export default [
  <Route key={ 1 } path="clients/:companyId/fleets/:id" component={ Fleet } />,
  <Route key={ 2 } path="clients/:companyId/fleets/:fleetId/invoices" component={ Invoices } />,
  <Route key={ 3 } path="clients/:companyId/fleets/:fleetId/invoices/:invoiceId" component={ Invoice } />
]
