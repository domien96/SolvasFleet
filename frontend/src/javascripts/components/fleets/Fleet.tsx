import React from 'react';
import { Link } from 'react-router';

import Header from '../app/Header.tsx';
import Card   from '../app/Card.tsx';

import fetchFleet    from '../../actions/fetch_fleet.ts';
import fetchVehicles from '../../actions/fetch_vehicles.ts';

import { group_by } from '../../utils/utils.ts';

interface vehicleProps {
  vehicle : Vehicle;
}
class VehicleRow extends React.Component<vehicleProps, {}> {
  render () {
    var { id, vin, brand, model, mileage } = this.props.vehicle;

    return (
      <Link to={ 'vehicles/' + id } className='vehicle'>
        <div>
          <span>Chassis Nummer:</span>
          <span>{ vin }</span>
        </div>
        <div>
          <span>Model:</span>
          <span>{ brand } { model }</span>
        </div>
        <div>
          <span>Mileage:</span>
          <span>{ mileage }</span>
        </div>
      </Link>
    );
  }
}

interface vehiclesProps {
  vehicles : any;
}
interface vehiclesState {
  type : string;
}
class Vehicles extends React.Component<vehiclesProps, vehiclesState> {
  constructor(props : vehiclesProps) {
    super(props);
    this.state = { type: null };
    this.subfleetVehicles = this.subfleetVehicles.bind(this);
    this.onClick = this.onClick.bind(this);
  }

  subfleetVehicles(key : string) : React.ReactElement<any> {
    if (this.state.type != key) {
      return null;
    }

    const vehicles = this.props.vehicles[key].map((v : Vehicle, i : number) => {
      return (<VehicleRow key={ i } vehicle={ v } />);
    });

    return (
      <div className='vehicles'>
        { vehicles }
      </div>
    )
  }

  onClick(newType : string) : void {
    var { type } = this.state;
    if (newType == type) {
      this.setState({ type: null });
    } else {
      this.setState({ type: newType });
    }
  }

  render() {
    const vehicles = Object.keys(this.props.vehicles).map((k, i) => {
      return (
        <div key={ i} className='subfleet-wrapper'>
          <div className='subfleet click' onClick={ () => this.onClick(k) }>
            <h3>{ k } ({ this.props.vehicles[k].length })</h3>
          </div>
          { this.subfleetVehicles(k) }
        </div>
      );
    });

    return (
      <div className='subfleets'>
        { vehicles }
      </div>
    );
  }
}

class Fleet extends React.Component<Fleet.Props, Fleet.State> {
  constructor(props : Fleet.Props) {
    super(props);
    this.state = {
      fleet: {},
      vehicles: {}
    }
  }

  componentDidMount() {
    var { id } = this.props.params;
    fetchFleet(id)
      .then((data : any) => {
        this.setState({ fleet: data });
      });
    // fetchVehicles('', id.toString())
    fetchVehicles('', "")
      .then((data : any) => {
        this.setState({ vehicles: group_by(data.data, 'type') })
      });
  }

  render () {
    return (
      <div>
        <Header>
          <h2>{ this.state.fleet.name }</h2>
        </Header>
        <div className='wrapper'>
          <Card>
            <div className='card-title'>
              <h5>Vehicles</h5>
            </div>
            <div className='card-content not-padded'>
              <Vehicles vehicles={ this.state.vehicles } />
            </div>
          </Card>
        </div>
      </div>
    )
  }
}

export default Fleet;
