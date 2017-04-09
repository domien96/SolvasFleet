import React from 'react';

import { fetchContractsByParams } from '../../actions/contract_actions.ts';
import { fetchFleet } from '../../actions/fleet_actions.ts'
import { fetchVehicle } from '../../actions/vehicle_actions.ts'
import { redirect_to } from'../../router.tsx';

import ContractsView from './ContractsView.tsx'

interface Props {
  vehicleId: number
}

interface State {
  contracts : ContractData[];
}

class Contracts extends React.Component<Props, State> {
	
	constructor(props : any) {
    super(props);
    this.state = { contracts: [] };
  }


  componentWillReveiveProps(nextProps: any){
    if(nextProps.vehicleId != this.props.vehicleId){
      let fleetId = this.getFleetId(this.props.vehicleId);
      let companyId = this.getCompanyId(fleetId);
      this.fetchContracts(this.props.vehicleId, fleetId, companyId);
    }
  }

  getFleetId(vehicleId: number){
    var vehicle : VehicleData;
    let success = (data : any) => vehicle = data;
    fetchVehicle(vehicleId, success);
    return vehicle.fleet;
  }

  getCompanyId(fleetId : number){
  	var fleet : FleetData;
  	let success = (data : any) => fleet = data;
    fetchFleet(fleetId, success);
    return fleet.company;
  }

  fetchContracts(companyId : number, fleetId : number, vehicleId : number) {
    fetchContractsByParams(companyId, fleetId, vehicleId, ((data : ContractsData) => {
      this.setState({ contracts: data.data })
    }));
  }

  handleClick(id : number) {
    redirect_to(`/vehicles/${this.props.vehicleId}/contracts/${id}`);
  }

  render(){
  	console.log(this.state.contracts)
  	if(this.state.contracts != []){
	  	return(
	  	<ContractsView contracts={ this.state.contracts } vehicleId={ this.props.vehicleId } onContractSelect={ this.handleClick }/>
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