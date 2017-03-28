import React from 'react';

import Header from '../app/Header.tsx';
import Card   from '../app/Card.tsx';
import NestedCheckbox from '../app/NestedCheckbox.tsx';
import SubfleetRow from './SubfleetRow.tsx';

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
