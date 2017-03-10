import React from 'react';
import { Link } from'react-router';


import Card       from '../app/Card.tsx';
import WrappedCol from '../app/WrappedCol.tsx';
import { InfoTable }  from '../tables/InfoTable.tsx';

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
      { key: 'company', label: 'vehicle.company' },
      { key: 'leasing_company', label: 'vehicle.leasing_company' },
      { key: 'chassis_number', label: 'vehicle.chassis_number' },
      { key: 'licence_plate', label: 'vehicle.license_plate' },
      { key: 'brand', label: 'vehicle.brand' },
      { key: 'model', label: 'vehicle.model' }
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
