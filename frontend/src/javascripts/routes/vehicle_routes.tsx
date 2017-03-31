import React from 'react';
import { Route, IndexRoute } from 'react-router';

import Vehicles from '../components/vehicles/Vehicles.tsx';
import Vehicle from '../components/vehicle/Vehicle.tsx';
import NoVehicle from '../components/vehicle/NoVehicle.tsx';
import AddVehicle from '../components/vehicle_form/AddVehicle.tsx';
import EditVehicle from '../components/vehicle_form/EditVehicle.tsx';

export default [
  <Route key={ 1 } path="vehicles/new" component={ AddVehicle } />,
  <Route key={ 2 } path="vehicles/:id/edit" component={ EditVehicle } />,
  <Route key={ 3 } path="vehicles" component={ Vehicles }>
    <IndexRoute component={ NoVehicle } />
    <Route path=":id" component={ Vehicle } />
  </Route>
]
