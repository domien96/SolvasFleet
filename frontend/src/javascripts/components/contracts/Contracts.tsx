import React from 'react';

import { fetchContractsByParams } from '../../actions/contract_actions.ts';
import { fetchFleet } from '../../actions/fleet_actions.ts'
import { redirect_to } from'../../router.tsx';

import ContractsView from './ContractsView.tsx'

interface Props {
	vehicleId : number; 
	fleetId : number; 
}

interface State {
  contracts : ContractData[];
}

class Contracts extends React.Component<Props, State> {
	
	constructor(props : any) {
    super(props);
    this.state = { contracts: [] };
  }

	componentDidMount() {
		var { vehicleId, fleetId } = this.props;
		console.log("vehicleId: "+vehicleId+" fleetId: "+fleetId);		
  }

  componentWillReceiveProps(nextProps : any){
		if(this.props.vehicleId != nextProps.vehicleId){
			var { vehicleId, fleetId } = this.props;
			var companyId = this.getCompanyId(fleetId);
			console.log(companyId)
    	this.fetchContracts(vehicleId, fleetId, companyId);
		}
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
    redirect_to(`/contracts/${id}`);
  }

  render(){
  	console.log(this.state.contracts)
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