import React from 'react';
import { Link } from'react-router';

import { fetchVehicle, deleteVehicle }  from '../../actions/vehicle_actions.ts';

import DetailTable from '../tables/DetailTable.tsx';
import Card      from '../app/Card.tsx';

import { th } from '../../utils/utils.ts';
import { redirect_to } from'../../router.tsx';


interface Props {
  params : { id : number };
  fetchVehicles : () => void;
}

interface State {
  vehicle : VehicleData;
}

class Vehicle extends React.Component<Props, State> {

  constructor() {
    super();
    this.state = { vehicle : { type: 'personalCar' } };
    this.deleteVehicle = this.deleteVehicle.bind(this);
  }

  fetchVehicle(id : number) {
    fetchVehicle(id, ((data : any) => {
      this.setState({ vehicle: data })
    }));
  }

  componentDidMount() {
    this.fetchVehicle(this.props.params.id);
  }

  componentWillReceiveProps(nextProps : Props) {
    if (nextProps.params.id != this.props.params.id) {
      this.fetchVehicle(nextProps.params.id);
    }
  }

  deleteVehicle(){
    var reloadVehicles = this.props.fetchVehicles;
    deleteVehicle(this.props.params.id, reloadVehicles);
    redirect_to('/vehicles');
  }

  render() {
    var { licensePlate, vin, brand, model, type, mileage, year, leasingCompany, value, fleet } = this.state.vehicle;
    var id = this.props.params.id;

    const data = [
      th('vehicle.fleet', fleet),
      th('vehicle.vin', vin),
      th('vehicle.licensePlate', licensePlate),
      th('vehicle.brand', brand),
      th('vehicle.model', model),
      th('vehicle.type', type),
      th('vehicle.mileage', mileage),
      th('vehicle.year', year),
      th('vehicle.value', value),
      th('vehicle.leasingCompany', leasingCompany),
    ];

    return (
    <Card>
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
        <div className='card-content'>
          <DetailTable data={ data }/>
        </div>
      </div>
    </Card>
    );
  }
}

export default Vehicle;
