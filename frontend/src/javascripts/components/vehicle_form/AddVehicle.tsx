import React from 'react';

import Header from '../app/Header.tsx';
import VehicleForm from './VehicleForm.tsx';

import { postVehicle } from '../../actions/vehicle_actions.ts';
import { hasError } from '../../utils/utils.ts';
import { redirect_to } from '../../routes/router.tsx';
import T from 'i18n-react';

interface State {
  errors: Form.Error[];
  vehicle: VehicleData;
}

class AddVehicle extends React.Component<{}, State> {
  constructor() {
    super();
    this.state = {
      errors: [],
      vehicle: { type: 'PersonalVehicle' },
    };
    this.handleChange = this.handleChange.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
  }

  public handleChange(field: Vehicle.Field, e: any): void {
    const newVehicle: VehicleData = this.state.vehicle;
    newVehicle[field] = e.target.value;
    this.setState({ vehicle: newVehicle });
  }

  public onSubmit(e: any): void {
    e.preventDefault();
    const setErrors = (es: Form.Error[]) => this.setState({ errors: es });
    const success = (data: any) => redirect_to(`/vehicles/${data.id}`);
    const fail = (data: any) => {
      setErrors(data.errors.map((es: any) => {
        return { field: es, error: 'null' };
      }));
    };

    postVehicle(this.changeDateFormat(this.state.vehicle), success, fail);
  }

  changeDateFormat(oldVehicle : VehicleData) {
    const { id, licensePlate, vin, brand, model, type, mileage, year, leasingCompany, value, fleet } = oldVehicle;
    let vehicle = {
      id: id,
      licensePlate: licensePlate,
      vin: vin,
      brand: brand,
      model: model,
      type: type,
      mileage: mileage,
      year: new Date(year),
      leasingCompany: leasingCompany,
      value: value,
      fleet: fleet
    };
    return vehicle;
  }

  render() {
    return (
      <div>
        <Header>
          <h2>{ T.translate('vehicle.addNew') }</h2>
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
