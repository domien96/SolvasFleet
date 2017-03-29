import React from 'react';

import Header     from '../app/Header.tsx';
import VehicleForm from './VehicleForm.tsx'

import { postVehicle } from '../../actions/vehicle_actions.ts';
import { hasError }  from '../../utils/utils.ts';
import { redirect_to } from'../../router.tsx';

class AddVehicle extends React.Component<{}, Vehicle.VForm.State> {

  constructor() {
    super();
    this.state = {
      errors: [],
      vehicle: { type: 'personalCar' }
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

    let success = (data : any) => redirect_to(`/vehicles/${data.id}`);
    let fail = (data : any) => {
      setErrors(data.errors.map((e : any) => {
        return { field: e, error: 'null' };
      }));
    }

    postVehicle(this.state.vehicle, success, fail);
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
