import React from 'react';
import { Link } from 'react-router';

import Checkbox from '../app/CheckBox.tsx';
import Header from '../app/Header.tsx';
import Card   from '../app/Card.tsx';

import fetchFleet    from '../../actions/fetch_fleet.ts';
import fetchVehicles from '../../actions/fetch_vehicles.ts';

import { group_by } from '../../utils/utils.ts';

interface vehicleProps {
  vehicle : Vehicle;
  checked : boolean;
  onChange : (k : string, id : number) => void;
}
class VehicleRow extends React.Component<vehicleProps, {}> {
  render () {
    var { id, vin, brand, model, mileage, type } = this.props.vehicle;

    return (
      <div className='tr'>
        <div className='td'>
          <input type='checkbox' className='checkbox' checked={ this.props.checked } onChange={ () => this.props.onChange(type, id) } />
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
  constructor(props : vehiclesProps) {
    super(props);
    this.state = { type: null, mappings: [] };
    this.subfleetVehicles = this.subfleetVehicles.bind(this);
    this.onClick = this.onClick.bind(this);
  }

  componentWillReceiveProps(props : vehiclesProps) {
    let m : any = {};
    Object.keys(props.vehicles).map((v) => {
      m[v] = props.vehicles[v].map((v : Vehicle) => {
        return { id: v.id, checked: false }
      });
    });
    this.setState({ mappings: m });
  }

  subfleetVehicles(key : string) : React.ReactElement<any> {
    if (this.state.type != key) {
      return null;
    }

    const vehicles = this.props.vehicles[key].map((v : Vehicle, i : number) => {
      return (<VehicleRow key={ i } vehicle={ v } checked={ this.state.mappings[key].find((f : any) => { return f.id == v.id }).checked } onChange={ this.onChangeChild.bind(this) } />);
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

  isChecked(k : string) {
    let b = this.state.mappings[k].map((o : any) => o.checked);
    for (var bb of b) {
      if (!bb) { return false; }
    }
    return true;
  }

  isIndeterminate(k : string) {
    let b = this.state.mappings[k].map((o : any) => o.checked);
    let checked = false;
    let unchecked = false;
    for (var bb of b) {
      if (bb) { checked = true; } else { unchecked = true; }
    }
    return checked && unchecked;
  }

  onChange(k : string) {
    if (this.isChecked(k)) {
      let m = this.state.mappings;
      m[k] = m[k].map(({ id } : any) => { return { id, checked: false }});
      this.setState({ mappings: m });
    } else {
      let m = this.state.mappings;
      m[k] = m[k].map(({ id } : any) => { return { id, checked: true }});
      this.setState({ mappings: m });
    }
  }

  onChangeChild(k : string, idd : number) {
    let m = this.state.mappings;
    m[k] = m[k].map(({ id, checked } : any) => {
      if (id == idd) {
        return { id: id, checked: !checked };
      } else {
        return { id, checked };
      }
    });
    this.setState({ mappings: m });
  }

  render() {
    const vehicles = Object.keys(this.props.vehicles).map((k, i) => {
      return (
        <div key={ i} className='subfleet-wrapper'>
          <div className='table'>
            <div className='subfleet-row tr'>
              <Checkbox className='checkbox td' checked={ this.isChecked.bind(this)(k) } indeterminate={ this.isIndeterminate.bind(this)(k) } onChange={ () => this.onChange(k) } />
              <h3 className='td' onClick={ () => this.onClick(k) }>
                { k } ({ this.props.vehicles[k].length })
              </h3>
            </div>
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
    fetchVehicles('', id.toString())
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
