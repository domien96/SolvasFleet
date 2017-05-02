import React from 'react';

import Header from '../app/Header.tsx';
import Card   from '../app/Card.tsx';
import NestedCheckbox from '../app/NestedCheckbox.tsx';
import SubfleetRow from './SubfleetRow.tsx';
import InvoiceActions from './InvoiceActions.tsx';
import T from 'i18n-react';

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
      fleet: {paymentPeriod:0,facturationPeriod:0,name:""},
      vehicles: []
    }
  }

  componentDidMount() {
    var { id } = this.props.params;
    //fetchFleet(id, success);
    fetchVehicles((data) => this.setState({ vehicles: data.data }), undefined, { fleet: id.toString() });
  }

  render () {

    let nodes = this.state.vehicles.map(({ id, type }) => { return { id, group: type } });

    return (
      <div>
        <Header>
          <h2>{ this.state.fleet.name }</h2>
        </Header>
        <InvoiceActions fleet={ this.props.params.id }/>
        <div className='wrapper'>
          <Card>
            <div className='card-title'>
              <h5>{ T.translate('vehicle.vehicles') }</h5>
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
