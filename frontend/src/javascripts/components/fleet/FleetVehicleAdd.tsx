import React from 'react';
import Card from '../app/Card.tsx';
import T from 'i18n-react';
import { Typeahead } from 'react-bootstrap-typeahead';
import { fetchVehicles, putVehicle } from '../../actions/vehicle_actions.ts';
import { Collapse } from 'react-bootstrap';
import classNames from 'classnames';

interface Props {
  fleet: number;
  refresh: () => void;
}

interface TypeaheadField {
  id: string;
  label: string;
}

interface State {
  show: boolean;
  vehicles: VehicleData[];
  currentVin: string;
  typeaheadFields: TypeaheadField[];
  submitDisabled: boolean;
  vehicle: VehicleData;
}

class FleetVehicleAdd extends React.Component<Props, State> {

  refs: {
    [string: string]: any;
    typeahead: any;
  }

  constructor() {
    super();
    this.state = { vehicles: [], currentVin: '', typeaheadFields: [], submitDisabled: true, vehicle: null, show: false };
    this.onTypeAheadChange = this.onTypeAheadChange.bind(this);
    this.getAllVehicles = this.getAllVehicles.bind(this);
    this.checkValidVin = this.checkValidVin.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
    this.onShowClick = this.onShowClick.bind(this);
  }

  getAllVehicles() {
    fetchVehicles((data) => this.setTypeaheadOptions(data.data.filter((a: VehicleData) => a.fleet === 0)));
    this.refs.typeahead.getInstance().clear();
  }

  componentDidMount() {
    this.getAllVehicles();
  }

  setTypeaheadOptions(vehicles: VehicleData[]) {
    const vins: TypeaheadField[] = vehicles.map( veh => ({ id: veh.id.toString(), label: veh.vin }));
    this.setState({ typeaheadFields: vins });
  }

  onTypeAheadChange(result: string) {
    this.setState({ currentVin: result, submitDisabled: true });
    this.checkValidVin(result);
  }

  checkValidVin(result: string) {
    const vinSize = 17;

    // There should exist 1 vehicle with that specific VIN in the database
    if(result.length === vinSize) {
      fetchVehicles(data => {
        if(data.data.length === 1) {
          this.setState({ vehicle: data.data[0], submitDisabled: false });
        }
        else {
          this.setState({ submitDisabled: true });
        }
      }, undefined, { vin: result });
    }
  }

  onSubmit() {
    const veh = this.state.vehicle;
    veh.fleet = this.props.fleet;
    putVehicle(veh.id, veh, () => {
      this.props.refresh();
      this.onShowClick();
      this.getAllVehicles();
    });
  }

  onShowClick(){
    this.setState({ show: !this.state.show });
  }

  render() {
    const buttonClass: string = classNames('btn', 'btn-success', 'pull-right', {disabled: this.state.submitDisabled});

    return (
      <div>
        <Card>
          <div className='card-content fleet-action-card' onClick={ this.onShowClick }>
          <h3><label>{ T.translate('fleet.addVehicle') }</label></h3>
          </div>
        </Card>

        <Collapse in={ this.state.show }>
          <Card>
            <div className='card-content fleets fleet-addvehicle'>
              <label>{ T.translate('fleet.chooseVin') }</label>
              <Typeahead onInputChange={ this.onTypeAheadChange } options={ this.state.typeaheadFields } ref="typeahead"/>
              <button className={ buttonClass } onClick={ this.onSubmit }>
                <span className='glyphicon glyphicon-plus' />{ T.translate('fleet.addToFleet') }</button>
            </div>
          </Card>
        </Collapse>
      </div>
    );
  }
}

export default FleetVehicleAdd;
