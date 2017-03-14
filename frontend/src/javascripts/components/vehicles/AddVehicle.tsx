import React from 'react';
import { browserHistory } from 'react-router';

import Header     from '../app/Header.tsx';
import VehicleForm from './VehicleForm.tsx'

import createVehicle from '../../actions/create_vehicle.ts';
import { hasError }  from '../../utils/utils.ts';


class AddVehicle extends React.Component<{}, Vehicle.VForm.State> {

  constructor() {
    super();
    this.state = {
      errors: [],
      vehicle: {}
    };
    this.handleChange = this.handleChange.bind(this);
    this.onSubmit     = this.onSubmit.bind(this);
  }

  public handleChange(field : Vehicle.Field, e : any) : void {
    var newVehicle : Vehicle = this.state.vehicle;
    newVehicle[field] = e.target.value;
    this.setState({ vehicle: newVehicle });
  }

  public onSubmit(e : any) : void {
    e.preventDefault();
    let setErrors = (e : Form.Error[]) => this.setState({ errors: e });

    createVehicle(this.state.vehicle)
    .then(function(response) {
      return response.json().then(function(data) {
        if (response.ok) {
          browserHistory.push('/vehicles/' + data.id);
        } else {
          console.log(data);
          setErrors(data.errors.map(function(e : any) {
            return { field: e.field, error: 'null' };
          }));
        }
      });
    });
  }

  render() {
    return (
      <div>
        <Header>
          <h2>Add A New Vehicle</h2>
        </Header>
        <VehicleForm
          vehicle={ this.state.vehicle }
          onSubmit={ this.onSubmit }
          handleChange={ this.handleChange }
          hasError={ hasError.bind(this) }
          errors={ this.state.errors }
          />
      </div>
    );
  }
}

export default AddVehicle;
