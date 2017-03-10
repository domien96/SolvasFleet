import React from 'react';
import { browserHistory, Link } from'react-router';

import Card       from '../app/Card.tsx';
import Header     from '../app/Header.tsx';
import { InfoTable, th } from '../tables/InfoTable.tsx';

import fetchVehicles from '../../actions/fetch_vehicles.ts';

interface OverviewProps {
  vehicles: Vehicle[];
}

class Overview extends React.Component<OverviewProps, {}> {

  constructor() {
    super();
    this.handleClick = this.handleClick.bind(this);
  }

  handleClick(id : number) {
    browserHistory.push('/vehicles/' + id);
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
      <InfoTable head={ tableHead } data={ this.props.vehicles } onClick={ this.handleClick } />
    );
  }
}

class Vehicles extends React.Component<{}, Vehicles.State> {

  constructor(props : {}) {
    super(props);
    this.state = { vehicles: [] };
  }

  componentDidMount() {
    this.fetchVehicles();
  }

  fetchVehicles() {
    fetchVehicles()
      .then((data : Vehicles.Data) => {
        this.setState({ vehicles: data.vehicles })
      });
  }

  render() {
    const children = React.Children.map(this.props.children,
      (child : any) => React.cloneElement(child, {
        fetchVehicles: this.fetchVehicles.bind(this)
      })
    );
    return (
      <div>
        <Header>
          <h2>Vehicles</h2>  
        </Header>
        <div className='wrapper'>
          <div className='row'>
            <div className='col-xs-12 col-md-7'>
              <Card>
                <div className='card-content'>
                  <Link to='/vehicles/new' className='btn btn-default pull-right'>
                    <span className='glyphicon glyphicon-plus' aria-hidden='true'></span>
                    Add new vehicle
                  </Link>
                  <Overview vehicles={ this.state.vehicles } />
                </div>
              </Card>
            </div>
            <div className='col-xs-12 col-md-5'>
              <Card>
                { children }
              </Card>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default Vehicles;
