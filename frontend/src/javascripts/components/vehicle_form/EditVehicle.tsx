import React from 'react';

import Header from '../app/Header.tsx';
import VehicleForm from './VehicleForm.tsx';

import { fetchVehicle, putVehicle } from '../../actions/vehicle_actions.ts';
import { hasError } from '../../utils/utils.ts';
import { redirect_to } from '../../routes/router.tsx';
import T from 'i18n-react';

interface Props {
  params: { id: number };
  fetchVehicles: () => void;
}

interface State {
  errors: Form.Error[];
  vehicle: VehicleData;
}

class EditVehicle extends React.Component<Props, State> {
  constructor() {
    super();
    this.state = {
      errors: [],
      vehicle: {},
    };
    this.handleChange = this.handleChange.bind(this);
    this.onSubmit     = this.onSubmit.bind(this);
  }

  componentDidMount() {
    fetchVehicle(this.props.params.id, (data: any) => {
      const vehicle: VehicleData = data;
      vehicle['year'] = vehicle.year.split('-')[0];
      this.setState({ vehicle: vehicle });
    });
  }

  handleChange(field: Vehicle.Field, e: any): any {
    const vehicle: VehicleData = this.state.vehicle;
    vehicle[field] = e.target.value;
    this.setState({ vehicle });
  }

  onSubmit(e: any): void {
    e.preventDefault();
    const setErrors = (es: Form.Error[]) => this.setState({ errors: es });
    const success = () => redirect_to(`/vehicles/${this.state.vehicle.id}`);
    const fail = (data: any) => {
      setErrors(data.errors.map((es: any) => ({ field: es.field, error: 'null' })));
    };
    console.log(this.state);
    putVehicle(this.state.vehicle.id, this.changeDateFormat(this.state.vehicle), success, fail);
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
          <h2>{ T.translate('vehicle.edit') }</h2>
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

export default EditVehicle;
