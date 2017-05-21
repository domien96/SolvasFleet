import React from 'react';
import { Route } from 'react-router';

import GlobalCommissions from '../components/commissions/GlobalCommissions.tsx';
import ClientCommissions from '../components/commissions/ClientCommissions.tsx';
import FleetCommissions from '../components/commissions/FleetCommissions.tsx';
import VehicleCommissions from '../components/commissions/VehicleCommissions.tsx';

export default [
  { path: 'commissions', component: GlobalCommissions },
  { path: 'commissions/clients/:companyId', component: ClientCommissions },
  { path: 'commissions/clients/:companyId/fleets/:fleetId', component: FleetCommissions },
  { path: 'commissions/clients/:companyId/fleets/:fleetId/vehicles/:vehicleId/:vehicleType', component: VehicleCommissions }
].map((props, i) => <Route key={ i} { ...props } />);
