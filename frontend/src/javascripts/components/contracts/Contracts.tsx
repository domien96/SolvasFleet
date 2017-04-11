import React from 'react';

import { fetchContractsByVehicle } from '../../actions/contract_actions.ts';
import { fetchContractsByFleet } from '../../actions/contract_actions.ts';
import { fetchContractsByCompany } from '../../actions/contract_actions.ts';

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

  componentWillReveiveProps(nextProps: any){
    var {vehicleId, companyId, fleetId} = nextProps;

    if(vehicleId != this.props.vehicleId 
      || companyId != this.props.fleetId
      || fleetId != this.props.companyId){
        this.updateContracts(vehicleId, companyId, fleetId);      
    }
  }

  componentWillUpdate(){
    if(this.props.vehicleId && !this.state.isInitialized){
      this.updateContracts(this.props.vehicleId, this.state.fleetByVehicle, this.state.companyByFleet);
    }
  }

  updateContracts(nextVehicleId : number, nextFleetId : number, nextCompanyId : number){
    if(nextVehicleId){
      this.setFleetId(nextVehicleId);
      if(this.state.fleetByVehicle){
        this.setCompanyId(this.state.fleetByVehicle);
      }
      if(this.state.fleetByVehicle && this.state.companyByFleet){
        this.fetchContractsByVehicle(this.state.companyByFleet, this.state.fleetByVehicle, nextVehicleId);
      }
    }
    else if(nextFleetId){
      this.setCompanyId(nextFleetId);
      this.fetchContractsByFleet(this.state.companyByFleet, nextFleetId);
    }
    else if(nextCompanyId){
      this.fetchContractsByCompany(nextCompanyId);
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