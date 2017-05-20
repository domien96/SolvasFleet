import React from 'react';

import Header from '../app/Header.tsx';
import VehicleFilter from './filters/VehicleFilter.tsx';
import VehicleListing from './VehicleListing.tsx';
import T from 'i18n-react';
import Auth from '../../modules/Auth.ts';
import DynamicGuiComponent from '../app/DynamicGuiComponent.tsx';

interface Props {
  response: ListResponse;
  onVehicleSelect: (id: number) => void;
  onFilter: (filter: VehicleFilterData) => void;
  fetchVehicles: (query: any) => void;
  errors: Form.Error[];
  handleChange: (e: any) => void;
  csvsuccess: boolean;
}

const Layout: React.StatelessComponent<Props> = props => {
  return (
    <div>
      <Header>
        <h2>{ T.translate('vehicle.vehicles') }</h2>
      </Header>
      <div className='wrapper'>
        <div className='row'>
          <DynamicGuiComponent authorized={ Auth.canReadFleetsOfCompany(0) }>
          <div className='col-xs-12 col-md-7'>
            <VehicleFilter onFilter = { props.onFilter } vehicles={ props.response.data } />
            <VehicleListing
              onSelect={ props.onVehicleSelect }
              addNewRoute='/vehicles/new'
              fetchModels={ props.fetchVehicles }
              modelName='vehicle'
              columns={ ['fleet', 'vin', 'licensePlate', 'type'] }
              response={ props.response }
              errors={ props.errors }
              handleChange={ props.handleChange }
              csvsuccess={ props.csvsuccess } />
          </div>
          </DynamicGuiComponent>
          <div className='col-xs-12 col-md-5'>
            { props.children }
          </div>
        </div>
      </div>
    </div>
  );
};

export default Layout;
