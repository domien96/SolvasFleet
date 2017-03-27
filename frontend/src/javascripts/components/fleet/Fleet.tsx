import React from 'react';
import { Link } from 'react-router';

import Checkbox from '../app/CheckBox.tsx';
import Header from '../app/Header.tsx';
import Card   from '../app/Card.tsx';
import NestedCheckbox from '../app/NestedCheckbox.tsx';

import { fetchFleet }    from '../../actions/fleet_actions.ts';
import { fetchVehicles } from '../../actions/vehicle_actions.ts';

import { group_by } from '../../utils/utils.ts';

interface vehicleProps {
  vehicle : Vehicle;
}
class VehicleRow extends React.Component<vehicleProps, {}> {
  static contextTypes = {
    childIsChecked:    React.PropTypes.func,
    childHandleChange: React.PropTypes.func
  }

  render () {
    var { id, vin, brand, model, mileage } = this.props.vehicle;

    return (
      <div className='tr'>
        <div className='td'>
          <input
            type='checkbox'
            className='checkbox'
            checked={ this.context.childIsChecked(id) }
            onChange={ () => this.context.childHandleChange(id) }
            />
        </div>
        <Link to={ 'vehicles/' + id } className='td'>
          <span>Chassis Nummer:</span>
          <span>{ vin }</span>
        </Link>
        <Link to={ 'vehicles/' + id } className='td'>
          <span>Model:</span>
          <span>{ brand } { model }</span>
        </Link>
        <Link to={ 'vehicles/' + id } className='td'>
          <span>Mileage:</span>
          <span>{ mileage }</span>
        </Link>
      </div>
    );
  }
}

interface vehiclesProps {
  vehicles : any;
}
interface vehiclesState {
  type : string;
  mappings : any;
}
class Vehicles extends React.Component<vehiclesProps, vehiclesState> {
  static contextTypes = {
    isChecked:       React.PropTypes.func,
    isIndeterminate: React.PropTypes.func,
    handleChange:    React.PropTypes.func
  }

  constructor(props : vehiclesProps) {
    super(props);
    this.state = { type: null, mappings: [] };
  }

  subfleetVehicles(key : string) : React.ReactElement<any> {
    if (this.state.type != key) {
      return null;
    }

    const vehicles = this.props.vehicles[key].map((v : Vehicle, i : number) => {
      return (<VehicleRow key={ i } vehicle={ v } />);
    });

    return (
      <div className='vehicles table'>
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
          <div className='table'>
            <div className='subfleet-row tr'>
              <Checkbox
                className='checkbox td'
                checked={ this.context.isChecked(k) }
                indeterminate={ this.context.isIndeterminate(k) }
                onChange={ () => this.context.handleChange(k) }
                />
              <h3 className='td' onClick={ () => this.onClick.bind(this)(k) }>
                { k } ({ this.props.vehicles[k].length })
              </h3>
            </div>
          </div>
          { this.subfleetVehicles.bind(this)(k) }
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
      vehicles: []
    }
  }

  componentDidMount() {
    var { id } = this.props.params;
    let success = (data : any) => this.setState({ fleet: data });
    fetchFleet(id, success);
    fetchVehicles((data) => this.setState({ vehicles: data.data}), undefined, { fleet: id.toString() });
  }

  render () {
    let nodes = this.state.vehicles.map(({ id, type }) => { return { id, group: type } });
    console.log(nodes)
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
              <NestedCheckbox values={ nodes }>
                <Vehicles vehicles={ group_by(this.state.vehicles, 'type') } />
              </NestedCheckbox>
            </div>
          </Card>
        </div>
      </div>
    )
  }
}

export default Fleet;
