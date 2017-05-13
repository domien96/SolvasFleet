import React from 'react';

import Header from '../app/Header.tsx';
import VehicleFilter from './filters/VehicleFilter.tsx';
import VehicleListing from './VehicleListing.tsx';
import T from 'i18n-react';

interface Props {
  response: ListResponse;
  onVehicleSelect: (id: number) => void;
  onFilter: (filter: VehicleFilterData) => void;
  fetchVehicles: (query: any) => void;
  onSubmit: (e: any) => void;
  errors: Form.Error[];
  handleChange: (e: any) => void;
}

const Layout: React.StatelessComponent<Props> = props => {
  return (
    <div>
      <Header>
        <h2>{ T.translate('vehicle.vehicles') }</h2>
      </Header>
      <div className='wrapper'>
        <div className='row'>
          <div className='col-xs-12 col-md-7'>
            <VehicleFilter onFilter = { props.onFilter } vehicles={ props.response.data } />
            <VehicleListing
              onSelect={ props.onVehicleSelect }
              addNewRoute='/vehicles/new'
              fetchModels={ props.fetchVehicles }
              modelName='vehicle'
              columns={ ['fleet', 'vin', 'licensePlate', 'type'] }
              response={ props.response }
              onSubmit={ props.onSubmit }
              errors={ props.errors }
              handleChange={ props.handleChange } />
          </div>
          <div className='col-xs-12 col-md-5'>
            { props.children }
          </div>
        </div>
      </div>
    </div>
  );
};

export default Layout;
