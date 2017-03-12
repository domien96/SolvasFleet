import React from 'react';
import { browserHistory, Link } from'react-router';

import fetchVehicle  from '../../actions/fetch_vehicle.ts';
import deleteVehicle from '../../actions/delete_vehicle.ts';

import Card       from '../app/Card.tsx';
import { DetailTable, th } from '../tables/DetailTable.tsx';

class Vehicle extends React.Component<Vehicle.Props, Vehicle.State> {

  constructor() {
    super();
    this.state = { vehicle : {} };
    this.deleteVehicle = this.deleteVehicle.bind(this);
  }

  fetchVehicle(id : number) {
    fetchVehicle(id)
      .then((data : any) => {
        this.setState({ vehicle: data })
      });
  }

  componentDidMount() {
    this.fetchVehicle(this.props.params.id);
  }

  componentWillReceiveProps(nextProps : Vehicle.Props) {
    if (nextProps.params.id != this.props.params.id) {
      this.fetchVehicle(nextProps.params.id);
    }
  }

  deleteVehicle(){
    var reloadVehicles = this.props.fetchVehicles;
    deleteVehicle(this.props.params.id)
    .then(function(this: any) {
      reloadVehicles();
    });
    browserHistory.push('/vehicles');
  }

  render() {
    var { licensePlate, vin, brand, model, type, mileage, year, leasingCompany, value, fleet } = this.state.vehicle;
    var id = this.props.params.id;

    const data = [
      th('vehicle.licensePlate', licensePlate),
      th('vehicle.vin', vin),
      th('vehicle.brand', brand),
      th('vehicle.model', model),
      th('vehicle.type', type),
      th('vehicle.mileage', mileage),
      th('vehicle.year', year),
      th('vehicle.value', value),
      th('company.leasingCompany', leasingCompany),
      th('company.fleet', fleet)
    ];

    return (
    <div>
      <div className='card-content user'>
        <h2>{ vin } </h2>
        <div className='row actions'>
          <div className='col-sm-6'>
            <Link to={ '/vehicles/' + id + '/edit' } className='btn btn-default form-control'>
              <span className='glyphicon glyphicon-edit' /> Edit
            </Link>
          </div>
          <div className='col-sm-6'>
            <button onClick = { this.deleteVehicle } className='btn btn-danger form-control'>
              <span className='glyphicon glyphicon-remove' /> Delete
            </button>
          </div>
        </div>
      </div>
      <Card>
        <div className='col-sm-12'>
          <div className='card-content'>
            <DetailTable data={ data }/>
          </div>
        </div>
      </Card>
    </div>
    );
  }
}

export default Vehicle;
