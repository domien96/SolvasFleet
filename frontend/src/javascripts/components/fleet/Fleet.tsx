import React from 'react';

import Header from '../app/Header.tsx';
import Card from '../app/Card.tsx';
import NestedCheckbox from '../app/NestedCheckbox.tsx';
import SubfleetRow from './SubfleetRow.tsx';
import InvoiceActions from './InvoiceActions.tsx';
import T from 'i18n-react';

import { fetchVehicles, deleteVehicle } from '../../actions/vehicle_actions.ts';
import { fetchFleet, putFleet} from '../../actions/fleet_actions.ts';
import { group_by } from '../../utils/utils.ts';
import Confirm from 'react-confirm-bootstrap';
import FleetActions from './FleetActions.tsx';
import FleetSettings from './FleetSettings.tsx';
import { deleteFleet } from '../../actions/fleet_actions.ts';
import FleetVehicleAdd from './FleetVehicleAdd.tsx';
import { redirect_to } from '../../routes/router.tsx';

interface VehiclesProps {
  vehicles: VehicleData[];
}
interface VehiclesState {
  type: string;
  mappings: any;
  groupedVehicles: any;
}

class Vehicles extends React.Component<VehiclesProps, VehiclesState> {
  static contextTypes = {
    handleChange: React.PropTypes.func,
    isChecked: React.PropTypes.func,
    isIndeterminate: React.PropTypes.func,
  };

  constructor(props: VehiclesProps) {
    super(props);
    this.state = { type: '', mappings: [], groupedVehicles: {} };
  }

  onClick(newType: string): void {
    const { type } = this.state;
    if (newType === type) {
      this.setState({ type: null });
    } else {
      this.setState({ type: newType });
    }
  }

  componentWillReceiveProps(props: VehiclesProps) {
    if(props.vehicles !== this.props.vehicles) {
      const groupedVehicles = group_by(props.vehicles, 'type');
      this.setState({ type: Object.keys(groupedVehicles)[0], groupedVehicles });
    }
  }

  render() {

    const vehicles = Object.keys(this.state.groupedVehicles).map((k, i) => {
      return (
        <SubfleetRow
          key={ i }
          type={ k }
          isChecked={ this.context.isChecked }
          isIndeterminate={ this.context.isIndeterminate }
          handleChange={ this.context.handleChange }
          onClick={ this.onClick.bind(this) }
          vehicles={ this.state.groupedVehicles[k] }
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
  showSettings: boolean;
  showAddVehicle: boolean;
  nodes: NestedCheckbox.Node[];
  checkedVehicles: number[];

  unsavedFleet : FleetData;
}

class Fleet extends React.Component<FleetProps, FleetState> {
  constructor(props: FleetProps) {
    super(props);
    this.state = {
      fleet: { paymentPeriod: 0, facturationPeriod: 0, name: "" },
      unsavedFleet: { paymentPeriod: 0, facturationPeriod: 0, name: "" },
      vehicles: [],
      showSettings: false,
      showAddVehicle: false,
      checkedVehicles: [],
      nodes: []
    }
    this.toggleShowSettings = this.toggleShowSettings.bind(this);
    this.handleChange = this.handleChange.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
    this.cb = this.cb.bind(this);
    this.archiveCheckedVehicles = this.archiveCheckedVehicles.bind(this);
    this.refresh = this.refresh.bind(this);
    this.archiveFleet = this.archiveFleet.bind(this);
  }

  componentDidMount() {
    this.refresh();
  }

  toggleShowSettings() {
    this.setState({ showSettings: !this.state.showSettings });
  }

  handleChange(field: Fleet.Field, e: any): any {
    const fleet: FleetData = this.state.unsavedFleet;
    fleet[field] = e.target.value;
    this.setState({ unsavedFleet: fleet });
  }

  onSubmit(e: any): void {
    e.preventDefault();
    let success = () => {
      this.toggleShowSettings();
      this.refresh();
    }
    putFleet(this.state.fleet.id, this.state.fleet.company, this.state.unsavedFleet, success);
  }

  refresh() {
    fetchFleet(this.props.params.id, this.props.params.companyId, (data) => {
      const clone = { ...data };
      this.setState({ fleet: data, unsavedFleet: clone })
    });

    fetchVehicles((data) => {
        const nodes = data.data.map((d: VehicleData) => ({ id: d.id, group: d.type }));
        this.setState({ checkedVehicles: [], vehicles: data.data, nodes });
      }, undefined, { fleet: this.props.params.id.toString() });
  }

  /* Callback function to obtain checked vehicles */
  cb(n: NestedCheckbox.Node[]) {
    const checked : number[] = n.filter(node => node.checked === true).map(node => node.id);
    this.setState({checkedVehicles: checked});
  }

  archiveCheckedVehicles() {
    for(let i = 0; i < this.state.checkedVehicles.length-1; i++) {
      deleteVehicle(this.state.checkedVehicles[i]);
    }
    // Refresh on last request
    deleteVehicle(this.state.checkedVehicles[this.state.checkedVehicles.length-1], () => this.refresh());
  }

  archiveFleet() {
    deleteFleet(this.state.fleet.id, this.state.fleet.company, () => {
      redirect_to(`/clients/${this.state.fleet.company}`)
    });
  }

  render () {
    return (
      <div>
        <Header>
          <h2 className='fleet-archive'>{ this.state.fleet.name }</h2><span>
          <Confirm
            onConfirm={ this.archiveFleet }
            body="Are you sure you want to archive this?"
            confirmText="Confirm Archive"
            title="Archive fleet">
            <button className='btn btn-lg btn-danger pull-right fleet-archive-button'>
              <span className='glyphicon glyphicon-remove' /> Archive fleet
            </button>
          </Confirm></span>
        </Header>
        <InvoiceActions fleet={ this.props.params.id } companyId={ this.props.params.companyId }/>
        <div className='wrapper'>
          <div className='row'>
              <div className='col-md-12 col-lg-2'>
                <FleetVehicleAdd fleet={ this.props.params.id } refresh={ this.refresh }/>
              </div>
              <div className='col-md-12 col-lg-5'>
                <FleetActions isDisabled={ this.state.checkedVehicles.length == 0 } callToArchive={ this.archiveCheckedVehicles } fleetId={ this.state.fleet.id } companyId={ this.state.fleet.company }/>
              </div>
            <div className='col-md-12 col-lg-5'>
              <FleetSettings onSettingsClick={ this.toggleShowSettings } showSettings={ this.state.showSettings }
                fleet={ this.state.unsavedFleet } handleChange={ this.handleChange } onSubmit = { this.onSubmit }/>
            </div>
          </div>
        <div className='row'>
          <div className='col-md-12'>
            <Card>
              <div className='card-title'>
                <h4 >{ T.translate('vehicle.vehicles') }</h4>
              </div>
              <div className='card-content'>
                <NestedCheckbox values={ this.state.nodes } cb={ this.cb }>
                  <Vehicles vehicles={ this.state.vehicles } />
                </NestedCheckbox>
              </div>
            </Card>
          </div>
        </div>
      </div>
    </div>
  );
  }
}

export default Fleet;
