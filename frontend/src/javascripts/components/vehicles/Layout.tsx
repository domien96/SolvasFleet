import React from 'react';

import Overview  from './Overview.tsx';
import Header    from '../app/Header.tsx';
import Card      from '../app/Card.tsx';
import VehicleFilter    from './filters/VehicleFilter.tsx'
import { Link }  from 'react-router';

interface CardProps {
  vehicles: Vehicle[];
  onVehicleSelect : (id : number) => void;
}

interface LayoutProps {
  vehicles: Vehicle[];
  onVehicleSelect : (id : number) => void;
  onFilter : (filter : VehicleFilterData) => void;
}

const MainCard : React.StatelessComponent<CardProps> = props => {
  return (
    <Card>
      <div className='card-content'>
        <Link to='/vehicles/new' className='btn btn-default pull-right'>
          <span className='glyphicon glyphicon-plus' aria-hidden='true'></span> Add new vehicle
        </Link>
        <Overview vehicles={ props.vehicles } onVehicleSelect={ props.onVehicleSelect } />
      </div>
    </Card>
  );
}

const Layout : React.StatelessComponent<LayoutProps> = props => {
  return (
    <div>
      <Header>
        <h2>Vehicles</h2>
      </Header>
      <div className='wrapper'>
        <div className='row'>
          <div className='col-xs-12 col-md-7'>
            <VehicleFilter onFilter = { props.onFilter } vehicles={ props.vehicles }/>
            <MainCard vehicles={ props.vehicles } onVehicleSelect={ props.onVehicleSelect } />
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
