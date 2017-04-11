import React from 'react';

import Header from '../app/Header.tsx';
import Card   from '../app/Card.tsx';
import NestedCheckbox from '../app/NestedCheckbox.tsx';
import SubfleetRow from './SubfleetRow.tsx';
import Contracts    from '../contracts/Contracts.tsx'

import { fetchFleet }    from '../../actions/fleet_actions.ts';
import { fetchVehicles } from '../../actions/vehicle_actions.ts';

import { group_by } from '../../utils/utils.ts';

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
        <SubfleetRow
          key={ i }
          type={ k }
          isChecked={ this.context.isChecked }
          isIndeterminate={ this.context.isIndeterminate }
          handleChange={ this.context.handleChange }
          onClick={ this.onClick.bind(this) }
          vehicles={ this.props.vehicles[k] }
          showVehicles={ this.state.type == k }
          />
      );
    });

    return (
      <div className='subfleets'>
        { vehicles }
      </div>
    );
  }
}

interface fleetProps {
  [ params : string ] : { [ id : string ] : number, companyId : number };
}

interface fleetState {
  fleet    : FleetData;
  vehicles : VehicleData[];
}

class Fleet extends React.Component<fleetProps, fleetState> {
  constructor(props : fleetProps) {
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
    fetchVehicles((data) => this.setState({ vehicles: data.data }), undefined, { fleet: id.toString() });
  }

  render () {
    var {fleet, vehicles} = this.state;

    let nodes = vehicles.map(({ id, type }) => { return { id, group: type } });

    return (
      <div>
        <Header>
          <h2>{ fleet.name }</h2>
        </Header>
        <div className='wrapper'>
          <Card>
            <div className='card-title'>
              <h5>Vehicles</h5>
            </div>
            <div className='card-content not-padded'>
              <NestedCheckbox values={ nodes }>
                <Vehicles vehicles={ group_by(vehicles, 'type') } />
              </NestedCheckbox>
            </div>
          </Card>
          <Contracts vehicleId={ null } fleetId={ this.props.params.id } companyId={ null }/> 
        </div>
      </div>
    )
  }
}

export default Fleet;
