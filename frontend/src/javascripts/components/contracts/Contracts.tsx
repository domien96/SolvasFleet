import React from 'react';

import { fetchContractsByVehicle, fetchContractsByFleet, fetchContractsByCompany, fetchContracts } from '../../actions/contract_actions.ts';

import { fetchFleet } from '../../actions/fleet_actions.ts'
import { fetchVehicle } from '../../actions/vehicle_actions.ts'
import { redirect_to } from'../../router.tsx';

import ContractsView from './ContractsView.tsx'

interface Props {
  vehicleId: number
  companyId: number
  fleetId: number
}

interface State {
  contracts : ContractData[];
  fleetByVehicle : number;
  companyByFleet : number;
  isInitialized : boolean;
}

class Contracts extends React.Component<Props, State> {
	
	constructor(props : any) {
    super(props);
    this.state = { contracts: [], fleetByVehicle: null, companyByFleet: null, isInitialized: false };
  }

  componentDidMount(){
    var {vehicleId, companyId, fleetId} = this.props;
    this.updateContracts(vehicleId, fleetId, companyId);
  }

  componentWillReceiveProps(nextProps: any){
    var {vehicleId, companyId, fleetId} = nextProps;

    if(vehicleId != this.props.vehicleId 
      || companyId != this.props.companyId
      || fleetId != this.props.fleetId){
        this.updateContracts(vehicleId, companyId, fleetId);      
    }
  }

  //Gets called whenever the state of the component changes
  componentWillUpdate(){
    if(this.props.vehicleId && !this.state.isInitialized){    // if Contracts is called for a specific vehicle and contracts are not yet fetched
      this.updateContracts(this.props.vehicleId, this.state.fleetByVehicle, this.state.companyByFleet);
    }
  }

  /*Checks if the contracts of a company, a fleet or a vehicle are requested
    If it's a vehicle check if the param is not null before passing it to a fetch function
  */
  updateContracts(nextVehicleId : number, nextFleetId : number, nextCompanyId : number){
    if(nextVehicleId){                                                  // if Contracts is called for a specific vehicle
      this.setFleetId(nextVehicleId);                                   // fetch the fleet of the vehicle
      if(this.state.fleetByVehicle){                                   // if the fleet fetch is complete
        this.setCompanyId(this.state.fleetByVehicle);                   // fetch the company of the fleet
      }
      if(this.state.fleetByVehicle && this.state.companyByFleet){      // if both fetches are complete fetch the contracts
        this.fetchContractsByVehicle(this.state.companyByFleet, this.state.fleetByVehicle, nextVehicleId);
      }
    }                                                                   
    else if(nextFleetId){                                               // if Contracts is called for a fleet
      this.setCompanyId(nextFleetId);
      this.fetchContractsByFleet(this.state.companyByFleet, nextFleetId);
    }
    else if(nextCompanyId){                                             // if Contracts is called for a company
      this.fetchContractsByCompany(nextCompanyId);
    }
    else{
      this.fetchContracts();
    }
  }

  setFleetId(vehicleId : number){
    let success = (data : any) => {
      this.setState({fleetByVehicle: data.fleet})
    }
    fetchVehicle(vehicleId, success);
  }

  setCompanyId(fleetId : number){
  	let success = (data : any) => {
      this.setState({companyByFleet: data.company})
    }
    fetchFleet(fleetId, success);
  }

  fetchContractsByVehicle(companyId : number, fleetId : number, vehicleId : number) {
    fetchContractsByVehicle(companyId, fleetId, vehicleId, ((data : ContractsData) => {
      this.setState({ contracts: data.data, isInitialized: true })
    }));
  }

  fetchContractsByFleet(companyId : number, fleetId : number) {
    fetchContractsByFleet(companyId, fleetId, ((data : ContractsData) => {
      this.setState({ contracts: data.data, isInitialized: true })
    }));
  }

  fetchContractsByCompany(companyId : number) {
    fetchContractsByCompany(companyId, ((data : ContractsData) => {
      this.setState({ contracts: data.data, isInitialized: true })
    }));
  }

  fetchContracts(){
    fetchContracts(((data : ContractsData) => {
      this.setState({ contracts: data.data })
    }));
  }

  handleClick(id : number) {
    redirect_to(`/contracts/${id}`);
  }

  render(){
  	if(this.state.contracts != []){
	  	return(
	  	<ContractsView contracts={ this.state.contracts } onContractSelect={ this.handleClick }/>
	  	);
		}
		return(
			<div>
				nothing here
			</div>
		);
  }
}

export default Contracts;