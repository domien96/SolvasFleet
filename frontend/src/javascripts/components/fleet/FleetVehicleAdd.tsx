import React from 'react';
import Card   from '../app/Card.tsx';
import T from 'i18n-react';
import { Typeahead } from 'react-bootstrap-typeahead';

import { fetchVehicles , putVehicle} from '../../actions/vehicle_actions.ts';
import { Collapse } from 'react-bootstrap';

interface Props {
  fleet : number;
  refresh : void;
}

interface State {
  show : boolean;
  vehicles : VehicleData[];
  currentVin : string;
  typeaheadFields : string[];
  submitDisabled : string;
  vehicle : VehicleData;
}

class FleetVehicleAdd extends React.Component<Props, State>{

	constructor(){
		super();
		this.state = {
      vehicles: [],currentVin:'',typeaheadFields:[], submitDisabled:' disabled', vehicle:null, show:false
		};
    this.onTypeAheadChange=this.onTypeAheadChange.bind(this);
    this.getAllVehicles=this.getAllVehicles.bind(this);
    this.checkValidVin=this.checkValidVin.bind(this);
    this.onSubmit=this.onSubmit.bind(this);
    this.onShowClick=this.onShowClick.bind(this);
	}

  getAllVehicles() {
    fetchVehicles((data)=>{
      this.setTypeaheadOptions(data.data.filter((a:VehicleData)=>a.fleet===0));
    });
  }

	componentDidMount(){
    this.getAllVehicles();
	}

  setTypeaheadOptions(vehicles:VehicleData[]){
    const vins : any[] = vehicles.map(veh=>{return {id:veh.id.toString(),label:veh.vin}});
    this.setState({typeaheadFields:vins});
  }

  onTypeAheadChange(result : string){
    this.setState({currentVin:result,submitDisabled:' disabled'});
    if(result.length===17)
      this.checkValidVin(result);
  }

  checkValidVin(result:string){
    fetchVehicles((data)=>{
      console.log(data);
      if(data.data.length==1)
        this.setState({vehicle:data.data[0],submitDisabled:''});
      else
        this.setState({submitDisabled:' disabled'});
    },()=>{
        this.setState({submitDisabled:' disabled'});
    },{vin:result});
  }

  onSubmit(){
    var veh=this.state.vehicle;
    veh.fleet=this.props.fleet;
    putVehicle(veh.id,veh,()=> {
      this.props.refresh();
      this.onShowClick();
    });
  }

  onShowClick(){
    this.setState({show:!this.state.show});
  }

  render() {
    return (
      <div>
        <Card>
        <div className='card-content settings' onClick={this.onShowClick}>
        <h3><label>{ T.translate('fleet.addVehicle') }</label></h3>
        <div className='actions pull-right'>
          <h3>
            <span className='glyphicon glyphicon-plus'/>
          </h3>
        </div></div></Card>
        <Collapse in={ this.state.show }>
        <Card>
          <div className='card-content fleets fleet-addvehicle'>
            <label>Choose VIN</label>
            <Typeahead onInputChange={ this.onTypeAheadChange } options={ this.state.typeaheadFields }/>
            <button className={'btn btn-success pull-right'+this.state.submitDisabled} onClick={this.onSubmit}>
              <span className='glyphicon glyphicon-plus' /> Add to fleet
            </button>
          </div>
        </Card>
        </Collapse></div>
    );
  }

}

export default FleetVehicleAdd;
