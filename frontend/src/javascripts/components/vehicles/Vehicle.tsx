import React from 'react';
import { browserHistory } from'react-router';

import fetchVehicle  from '../../actions/fetch_vehicle.ts';
import deleteVehicle from '../../actions/delete_vehicle.ts';

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
    var { license_plate, chassis_number, brand, model, type, kilometer_count, year, leasing_company, value, company } = this.state.vehicle;

    return (
      <div className='card-content vehicle'>
        <h2> Vehicle:  { chassis_number }</h2>
        <div className='row actions'>
          <div className='col-sm-6'>
            <button className='btn btn-default form-control'>
              <span className='glyphicon glyphicon-edit' />
              Edit
            </button>
          </div>
          <div className='col-sm-6'>
            <button onClick = { this.deleteVehicle } className='btn btn-danger form-control'>
              <span className='glyphicon glyphicon-remove' />
              Delete
            </button>
          </div>
        </div>
        <h5>Information</h5>
        <div>license_plate: { license_plate }</div>
        <div>company: { company }</div>
        <div>leasing_company: { leasing_company }</div>
        <div>brand: { brand }</div>
        <div>model: { model }</div>
        <div>type: { type }</div>
        <div>kilometer_count: { kilometer_count }</div>
        <div>year: { year }</div>
        <div>value: { value }</div>
      </div>
    );
  }
}

export default Vehicle;
