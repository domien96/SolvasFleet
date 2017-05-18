import React from 'react';

import Header from '../app/Header.tsx';
import Card from '../app/Card.tsx';
import NestedCheckbox from '../app/NestedCheckbox.tsx';
import SubfleetRow from './SubfleetRow.tsx';
import InvoiceActions from './InvoiceActions.tsx';
import FleetActions from './FleetActions.tsx';
import T from 'i18n-react';

import { fetchVehicles } from '../../actions/vehicle_actions.ts';
import { group_by } from '../../utils/utils.ts';

interface VehiclesProps {
  vehicles: any;
}
interface VehiclesState {
  type: string;
  mappings: any;
}
class Vehicles extends React.Component<VehiclesProps, VehiclesState> {
  static contextTypes = {
    handleChange: React.PropTypes.func,
    isChecked: React.PropTypes.func,
    isIndeterminate: React.PropTypes.func,
  };

  constructor(props: VehiclesProps) {
    super(props);
    this.state = { type: null, mappings: [] };
  }

  onClick(newType: string): void {
    const { type } = this.state;
    if (newType === type) {
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
          showVehicles={ this.state.type === k }
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

interface FleetProps {
  [ params: string ]: { [ id: string ]: number, companyId: number };
}

interface FleetState {
  fleet: FleetData;
  vehicles: VehicleData[];
}

class Fleet extends React.Component<FleetProps, FleetState> {
  constructor(props: FleetProps) {
    super(props);
    this.state = {
      fleet: { paymentPeriod: 0, facturationPeriod: 0, name: '' },
      vehicles: [],
    };
  }

  componentDidMount() {
    const { id } = this.props.params;
    // fetchFleet(id, success);
    fetchVehicles((data) => this.setState({ vehicles: data.data }), undefined, { fleet: id.toString() });
  }

  render() {
    const nodes = this.state.vehicles.map(({ id, type }) => ({ id, group: type }));

    return (
      <div>
        <Header>
          <h2>{ this.state.fleet.name }</h2>
        </Header>
        <InvoiceActions fleet={ this.props.params.id } companyId={ this.props.params.companyId }/>
        <FleetActions/>
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
    );
  }
}

export default Fleet;
