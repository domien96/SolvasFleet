import React from 'react';

import { fetchContractsByVehicle, fetchContractsByFleet, fetchContractsByCompany, fetchContracts } from '../../actions/contract_actions.ts';

import { fetchFleet } from '../../actions/fleet_actions.ts'
import { fetchVehicle } from '../../actions/vehicle_actions.ts'
import { redirect_to } from'../../router.tsx';
import Listing from '../app/Listing.tsx';
import ContractsView from './ContractsView.tsx'

interface Props {
  vehicleId: number
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

  fetchContracts(){
    fetchContracts(((data : ContractsData) => {
      this.setState({ contracts: data.data })
    }),query={vehicle:vehicleId});
  }

  handleClick(id : number) {
    redirect_to(`/contracts/${id}`);
  }

  componentDidMount() {
    this.fetchContracts();
  }

  render(){
  	if(this.state.contracts != []){
	  	return(
          <Listing onSelect={this.handleClick} addNewRoute='/contracts/new' fetchModels={this.fetchContracts} models={this.state.contracts} modelName='contract' columns={['id','type','vehicle']}/>
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
