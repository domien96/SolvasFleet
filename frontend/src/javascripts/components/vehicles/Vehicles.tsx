import React from 'react';
import { browserHistory, Link } from 'react-router';
import { DropdownButton, MenuItem } from 'react-bootstrap';

import Card       from '../app/Card.tsx';
import Header     from '../app/Header.tsx';
import InfoTable from '../tables/InfoTable.tsx';

import fetchVehicles from '../../actions/fetch_vehicles.ts';
import { th } from '../../utils/utils.ts';

interface OverviewProps {
  vehicles: Vehicle[];
}

class Overview extends React.Component<OverviewProps, {}> {

  constructor() {
    super();
    this.handleClick = this.handleClick.bind(this);
  }

  handleClick(id : number) {
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
	onChange : (fleet : number) => void;
}

interface OptionsState {
	fleetID : number;
}

class Options extends React.Component<OptionsProps, OptionsState>{

	render(){
		return(
		  	<div className='row actions'>
	      	  <div className='col-md-2'>
	      	  	<div>
	      	      <DropdownButton className='btn btn-default' title="Vehicle type">
	      	      	<MenuItem onSelect={ () => this.props.onSelect('') }>All vehicles</MenuItem>
			        <MenuItem onSelect={ () => this.props.onSelect('personal car') }>Personal Car</MenuItem>
			        <MenuItem onSelect={ () => this.props.onSelect('van') }>Van (light truck)</MenuItem>
			        <MenuItem onSelect={ () => this.props.onSelect('semi-trailer') }>Semi-trailer</MenuItem>
			        <MenuItem onSelect={ () => this.props.onSelect('trailer') }>Trailer</MenuItem>
			        <MenuItem onSelect={ () => this.props.onSelect('truck') }>Truck</MenuItem>
			      </DropdownButton>
			    </div>
			      <form>
			        <label> Fleet ID:
			          <input name='fleetID' type='number' value={ this.state.fleetID } onChange={ () => this.props.onChange(this.state.fleetID) } />
			        </label>
			      </form>
			    <div>
			    	Todo inputfield fleet
			    </div>

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
    this.state = { vehicles: [], type: '', fleet: null };
    this.handleSelect = this.handleSelect.bind(this);
    this.handleFleetChange = this.handleFleetChange.bind(this);
  }

  componentDidMount() {
    this.fetchVehicles();
  }

  fetchVehicles() {
    fetchVehicles(this.state.type, this.state.fleet)
      .then((data : Vehicles.Data) => {
        this.setState({ vehicles: data.data })
      });
  }

  handleSelect(newType : string){
  	this.setState({ type: newType })
  	this.fetchVehicles();
  }

  handleFleetChange(newFleet : number){
  	this.setState({ fleet: newFleet })
  	this.fetchVehicles();
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
            <div className='col-xs-12 col-md-8'>
              <Card>
                <div className='card-content'>
                  <Options onSelect={ this.handleSelect } onChange={ this.handleFleetChange }/>	
                  <Overview vehicles={ this.state.vehicles } />
                </div>
              </Card>
            </div>
            <div className='col-xs-12 col-md-4'>
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
