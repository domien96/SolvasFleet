import React from 'react';
import { Link } from'react-router';


import Card       from '../app/Card.tsx';
import WrappedCol from '../app/WrappedCol.tsx';
import { InfoTable, th }  from '../tables/InfoTable.tsx';

import fetchVehicles from '../../actions/fetch_vehicles.ts';

class Vehicles extends React.Component<{}, Vehicles.State> {

  constructor(props : {}) {
    super(props);
    this.state = { vehicles: [] };
    this.handleClick = this.handleClick.bind(this);
  }

  componentDidMount() {
    fetchVehicles()
      .then((data : Vehicles.Data) => {
        this.setState({ vehicles: data.vehicles })
      });
  }

  handleClick(){

  }

  render() {
    const tableHead = [
      th('company', 'vehicle.company'),
      th('leasing_company', 'vehicle.leasing_company'),
      th('chassis_number', 'vehicle.chassis_number') ,
      th('licence_plate', 'vehicle.license_plate') ,
      th('brand', 'vehicle.brand') ,
      th('model', 'vehicle.model') 
    ]

    return (
      <WrappedCol>
        <Card className='text-center' >
          <div className='card-title'>
            <h2>Vehicles</h2>
            <Link to='vehicles/new'>
              <span className='glyphicon glyphicon-plus' aria-hidden='true'></span>
            </Link>
          </div>
          <div className='card-content'>
            <InfoTable head={ tableHead } data={ this.state.vehicles } onClick={this.handleClick}/>
          </div>
        </Card>
      </WrappedCol>
    );
  }
}

export default Vehicles;
