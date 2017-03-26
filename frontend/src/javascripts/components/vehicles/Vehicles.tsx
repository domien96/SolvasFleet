import React from 'react';
import { browserHistory, Link } from 'react-router';
import { ButtonGroup, DropdownButton, MenuItem } from 'react-bootstrap';

import Card       from '../app/Card.tsx';
import Header     from '../app/Header.tsx';
import InfoTable from '../tables/InfoTable.tsx';

import { fetchVehicles } from '../../actions/vehicle_actions.ts';
import { th } from '../../utils/utils.ts';

import T     from 'i18n-react';

interface OverviewProps {
  vehicles: Vehicle[];
}

class Overview extends React.Component<OverviewProps, {}> {

  constructor() {
    super();
    this.handleClick = this.handleClick.bind(this);
  }

  handleClick(id : string) {
    browserHistory.push('/vehicles/' + id);
  }

  render() {
    const tableHead = [
      th('fleet', 'vehicle.fleet'),
      th('vin', 'vehicle.vin') ,
      th('licensePlate', 'vehicle.licensePlate') ,
      th('type', 'vehicle.type')
    ];

    return (
      <InfoTable head={ tableHead } data={ this.props.vehicles } onClick={ this.handleClick } />
    );
  }
}

interface OptionsProps {
  onSelect : (type : string) => void;
  onChange : (fleet : string) => void;
}

interface OptionsState {
  fleetId : string;
  title: string;
}

class Options extends React.Component<OptionsProps, OptionsState>{

  constructor(){
    super();
    this.state = { fleetId : '', title: 'All vehicles' }
    this.handleChange = this.handleChange.bind(this);
    this.handleSelect = this.handleSelect.bind(this);
  }

  shouldComponentUpdate(_nextProps : OptionsProps, nextState : OptionsState) {
    var { fleetId, title }   = this.state;
    var { fleetId: nFleetId, title: nTitle } = nextState;

    if (fleetId != nFleetId || title != nTitle) { return true; }
    return false;
  }

  handleChange(event : any){
    this.setState( {fleetId : event.target.value} )
    this.props.onChange(event.target.value);
  }

  constructor(){
    super();
    this.state = { fleetId : '', title: 'All vehicles' }
    this.handleChange = this.handleChange.bind(this);
    this.handleSelect = this.handleSelect.bind(this);
  }

  handleChange(event : any){
    this.setState( {fleetId : event.target.value} )
    this.props.onChange(event.target.value);
  }

  render(){
    return(
      <div className='row actions'>
        <ButtonGroup justified>
          <div className='col-md-3'>
            <div>
              <DropdownButton className='btn btn-default' title={ this.state.title } id='vehicleTypeChoice' >
                <MenuItem onSelect={ () => this.handleSelect('') }>{ T.translate('vehicle.options.allVehicles') }</MenuItem>
                <MenuItem onSelect={ () => this.handleSelect('personalCar') }>{ T.translate('vehicle.options.personalCar') }</MenuItem>
                <MenuItem onSelect={ () => this.handleSelect('van') }>{ T.translate('vehicle.options.van') }</MenuItem>
                <MenuItem onSelect={ () => this.handleSelect('semiTrailer') }>{ T.translate('vehicle.options.semiTrailer') }</MenuItem>
                <MenuItem onSelect={ () => this.handleSelect('trailer') }>{ T.translate('vehicle.options.trailer') }</MenuItem>
                <MenuItem onSelect={ () => this.handleSelect('truck') }>{ T.translate('vehicle.options.truck') }</MenuItem>
              </DropdownButton>
            </div>
          </div>
          <div className='col-md-5'>
            <form>
              <span>
                <label className='col-md-7 lab-padding'>
                  <div>Fleet ID:</div>
                </label>
                <div className='input-padding align-left'><input className='col-md-4' type='number' value={ this.state.fleetId } onChange={ this.handleChange } /></div>
              </span>
            </form>
          </div>
          <div className='col-md-3'>
            <div>
              <Link to='/vehicles/new' className='btn btn-default'>
                <span className='glyphicon glyphicon-plus' aria-hidden='true'></span> Add new vehicle
              </Link>
            </div>
          </div>
        </ButtonGroup>
      </div>
    );
  }
}

class Vehicles extends React.Component<{}, Vehicles.State> {

  constructor(props : {}) {
    super(props);
    this.state = { vehicles: [], type: '', fleet: '' };
    this.handleSelect = this.handleSelect.bind(this);
    this.handleFleetChange = this.handleFleetChange.bind(this);
  }

  componentDidMount() {
    var { type, fleet } = this.state;
    this.fetchVehicles(type, fleet);
  }

  fetchVehicles(type : string, fleet : string) {
    fetchVehicles((data) => this.setState({ vehicles: data.data }), undefined, { type, fleet })
  }

  handleSelect(newType : string){
    this.setState({ type: newType })
    this.fetchVehicles(newType, this.state.fleet);
  }

  handleFleetChange(newFleet : string){
    this.setState({ fleet: newFleet })
    this.fetchVehicles(this.state.type, newFleet);
  }

  render() {
    const children = React.Children.map(this.props.children,
      (child : any) => React.cloneElement(child, {
        fetchVehicles: this.fetchVehicles.bind(this)
      })
    );
    return (
      <div>
        <Header>
          <h2>Vehicles</h2>
        </Header>
        <div className='wrapper'>
          <div className='row'>
            <div className='col-xs-12 col-md-7'>
              <Card>
                <div className='card-content'>
                  <Options onSelect={ this.handleSelect } onChange={ this.handleFleetChange }/>
                  <Overview vehicles={ this.state.vehicles } />
                </div>
              </Card>
            </div>
            <div className='col-xs-12 col-md-5'>
              <Card>
                { children }
              </Card>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default Vehicles;
