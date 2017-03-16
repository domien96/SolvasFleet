import React from 'react';
import { VEHICLES_URL } from '../../constants/constants.ts';
import { browserHistory } from 'react-router';

import Header from '../app/Header.tsx';
import VehicleForm from './VehicleForm.tsx';

import fetchVehicle    from '../../actions/fetch_vehicle.ts';
import { hasError } from '../../utils/utils.ts';

class EditVehicle extends React.Component<Vehicle.Props, Vehicle.VForm.State> {
  constructor() {
    super();
    this.state = {
      errors: [],
      vehicle: {}
    };
    this.handleChange = this.handleChange.bind(this);
    this.onSubmit     = this.onSubmit.bind(this);
  }

  componentDidMount() {
    fetchVehicle(this.props.params.id)
      .then((data : any) => {
        this.setState({ vehicle: data })
      });
  }

  handleChange(field : Vehicle.Field, e : any) : any {
    var vehicle : Vehicle = this.state.vehicle;
    vehicle[field] = e.target.value;
    this.setState({ vehicle });
  }

  onSubmit(e : any) : void {
    e.preventDefault();
    let setErrors = (e : Form.Error[]) => this.setState({ errors: e });

    fetch(VEHICLES_URL + '/' + this.state.vehicle.id, {
      method: 'PUT',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(this.state.vehicle)
    })
    .then(function(response) {
      return response.json().then(function(data) {
        if (response.ok) {
          browserHistory.push('/vehicles/' + data.id);
        } else {
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
          <h2>Edit Vehicle</h2>
        </Header>
        <VehicleForm
          vehicle={ this.state.vehicle }
          onSubmit={ this.onSubmit }
          handleChange={ this.handleChange }
          hasError={ hasError.bind(this) }
          errors={ this.state.errors }
          />
      </div>
    )
  }
}

export default EditVehicle;
