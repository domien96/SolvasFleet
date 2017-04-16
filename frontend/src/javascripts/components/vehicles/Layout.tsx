import React from 'react';

import Header    from '../app/Header.tsx';
import VehicleFilter    from './filters/VehicleFilter.tsx'
import Listing from '../app/Listing.tsx';

interface Props {
  vehicles: VehicleData[];
  onVehicleSelect : (id : number) => void;
  onFilter : (filter : VehicleFilterData) => void;
  fetchVehicles: (query:any) =>void
}

const Layout : React.StatelessComponent<Props> = props => {
  return (
    <div>
      <Header>
        <h2>Vehicles</h2>
      </Header>
      <div className='wrapper'>
        <div className='row'>
          <div className='col-xs-12 col-md-7'>
            <VehicleFilter onFilter = { props.onFilter } vehicles={ props.vehicles }/>
            <Listing onSelect={props.onVehicleSelect} addNewRoute='/vehicles/new' fetchModels={props.fetchVehicles} modelName='vehicle'
              columns={['fleet','vin','licensePlate','type']} models={props.vehicles}/>
          </div>
          <div className='col-xs-12 col-md-5'>
            { props.children }
          </div>
        </div>
      </div>
    </div>
  );
}

export default Layout;
