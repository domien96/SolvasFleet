import React from 'react';
import { browserHistory, Link } from 'react-router';
import { ButtonGroup, DropdownButton, MenuItem } from 'react-bootstrap';

import Card       from '../app/Card.tsx';
import Header     from '../app/Header.tsx';
import InfoTable from '../tables/InfoTable.tsx';

import fetchVehicles from '../../actions/fetch_vehicles.ts';
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

	handleChange(event : any){
		this.setState( {fleetId : event.target.value} )
		this.props.onChange(event.target.value);
	}

	handleSelect(type : string){	
		if(type == ''){
			let x : string = T.translate('vehicle.options.allVehicles');
			this.setState( {title : x} );
		}
		else{
			let x : string = T.translate('vehicle.options.' + type);
			this.setState( {title: x} );
		}
		this.props.onSelect(type);
	}

	render(){
		return(
		  	<div className='row actions'>
	      	  <div className='col-md-3'>
	      	  	<div>
	      	  	<ButtonGroup justified>
	      	      <DropdownButton className='btn btn-default' title={ this.state.title } id='vehicleTypeChoice' >
	      	      	<MenuItem onSelect={ () => this.handleSelect('') }>{ T.translate('vehicle.options.allVehicles') }</MenuItem>
			        <MenuItem onSelect={ () => this.handleSelect('personalCar') }>{ T.translate('vehicle.options.personalCar') }</MenuItem>
			        <MenuItem onSelect={ () => this.handleSelect('van') }>{ T.translate('vehicle.options.van') }</MenuItem>
			        <MenuItem onSelect={ () => this.handleSelect('semiTrailer') }>{ T.translate('vehicle.options.semiTrailer') }</MenuItem>
			        <MenuItem onSelect={ () => this.handleSelect('trailer') }>{ T.translate('vehicle.options.trailer') }</MenuItem>
			        <MenuItem onSelect={ () => this.handleSelect('truck') }>{ T.translate('vehicle.options.truck') }</MenuItem>
			      </DropdownButton>
			      </ButtonGroup>
			    </div>
			  </div>    
			  <div className='col-md-6'>
			    <div>
			    	<form>
			        <label> Fleet ID:
			          <input name='fleetID' type='number' value={ this.state.fleetId } onChange={ this.handleChange } />
			        </label>
			      </form>
			    </div>
			  </div>
	      	  <div className='col-md-2'>
			    <div>
	      	      <Link to='/vehicles/new' className='btn btn-default pull-right'>
                    <span className='glyphicon glyphicon-plus' aria-hidden='true'></span> Add new vehicle
                  </Link>
                </div>
		      </div>
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
  	console.log(this.state)
    fetchVehicles(type, fleet)
      .then((data : Vehicles.Data) => {
        this.setState({ vehicles: data.data })
      });
    return true;  
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
