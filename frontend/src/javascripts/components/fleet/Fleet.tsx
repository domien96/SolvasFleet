import React from 'react';

import Header from '../app/Header.tsx';
import Card   from '../app/Card.tsx';
import NestedCheckbox from '../app/NestedCheckbox.tsx';
import SubfleetRow from './SubfleetRow.tsx';
import InvoiceActions from './InvoiceActions.tsx';
import T from 'i18n-react';

import { fetchVehicles } from '../../actions/vehicle_actions.ts';
import { fetchFleet, putFleet } from '../../actions/fleet_actions.ts';
import { group_by } from '../../utils/utils.ts';
import FleetForm from '../fleets/FleetForm.tsx';
import { Collapse } from 'react-bootstrap';
import Confirm from 'react-confirm-bootstrap';
import FleetActions from './FleetActions.tsx';
import FleetSettings from './FleetSettings.tsx';
import FleetVehicleAdd from './FleetVehicleAdd.tsx';

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
  showSettings : boolean;
  showAddVehicle : boolean;
}

class Fleet extends React.Component<fleetProps, fleetState> {
  constructor(props : fleetProps) {
    super(props);
    this.state = {
      fleet: {paymentPeriod:0,facturationPeriod:0,name:""},
      vehicles: [],
      showSettings : false,
      showAddVehicle : false
    }
    this.onSettingsClick=this.onSettingsClick.bind(this);
    this.handleChange=this.handleChange.bind(this);
    this.onSubmit=this.onSubmit.bind(this);
    this.showAddVehicle=this.showAddVehicle.bind(this);
  }

  componentDidMount() {
    var { id, companyId} = this.props.params;
    fetchFleet(id,companyId, (data)=> this.setState({fleet:data}));
    fetchVehicles((data) => this.setState({ vehicles: data.data }), undefined, { fleet: id.toString() });
  }

  onSettingsClick() {
    this.setState({showSettings:!this.state.showSettings});
  }

  handleChange(field : Fleet.Field, e : any) : any {
    var fleet : FleetData = this.state.fleet;
    fleet[field] = e.target.value;
    this.setState({ fleet });
  }

  onSubmit(e : any) : void {
    e.preventDefault();
    //let setErrors = (e : Form.Error[]) => this.setState({ errors: e });
    let success = () => this.onSettingsClick();
    /*let fail = (data : any) => {
      setErrors(data.errors.map(function(e : any) {
        return { field: e, error: 'null' };
      }));*/
    //}
    putFleet(this.state.fleet.id,this.state.fleet.company,this.state.fleet,success);
  }

  showAddVehicle() {
    this.setState({showAddVehicle:!this.state.showAddVehicle});
  }

  render () {

    let nodes = this.state.vehicles.map(({ id, type }) => { return { id, group: type } });

    return (
      <div>
        <Header>
          <h2 className='fleet-archive'>{ this.state.fleet.name }</h2>
          <Confirm
            onConfirm={ null }
            body="Are you sure you want to archive this?"
            confirmText="Confirm Archive"
            title="Archive fleet">
            <button className='btn btn-lg btn-danger pull-right'>
              <span className='glyphicon glyphicon-remove' /> Archive fleet
            </button>
          </Confirm>
        </Header>
        <InvoiceActions fleet={ this.props.params.id }/>

        <div className='wrapper'>
          <div className='row'>


              <div className='col-xs-12 col-md-4'>
              <FleetVehicleAdd fleet={this.props.params.id}/>
              </div>


              <div className='col-xs-12 col-md-4'>
                <FleetActions isDisabled={true} callToArchive={this.test}/>
              </div>

            <div className='col-xs-12 col-md-4'>
              <FleetSettings onSettingsClick={this.onSettingsClick} showSettings={this.state.showSettings} fleet={this.state.fleet} handleChange={ this.handleChange } onSubmit = {this.onSubmit}/>
            </div>
          </div>


        <div className='row'>
          <div className='col-md-12'>
            <Card>
              <div className='card-title'>
                <h4 >{ T.translate('vehicle.vehicles') }</h4>

              </div>
              <div className='card-content'>
                <NestedCheckbox values={ nodes }>
                  <Vehicles vehicles={ group_by(this.state.vehicles, 'type') } />
                </NestedCheckbox>
              </div>
            </Card>
          </div>


        </div>
      </div>
    </div>
    )
  }
}

export default Fleet;
