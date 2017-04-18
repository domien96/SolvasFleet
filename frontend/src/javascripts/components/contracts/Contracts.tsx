import React from 'react';

import { fetchContractsByVehicle, fetchContractsByFleet, fetchContractsByCompany, fetchContracts } from '../../actions/contract_actions.ts';

import { fetchFleet } from '../../actions/fleet_actions.ts'
import { fetchVehicle } from '../../actions/vehicle_actions.ts'
import { redirect_to } from'../../router.tsx';
import Listing from '../app/Listing.tsx';

interface Props {
  fetchMethod: any //succes,fail
  vehicleId: number
  fleetId: number
  companyId: number
}

interface State {
  response: ListResponse;
  fleetByVehicle : number;
  companyByFleet : number;
  isInitialized : boolean;
}

class Contracts extends React.Component<Props, State> {

	constructor(props : any) {
    super(props);
    this.state = { contracts: [], fleetByVehicle: null, companyByFleet: null, isInitialized: false ,  response:{total:0,first : 0, last : 0, limit : 0, offset : 0, previous : 0, next : 0,data:[]}};
    this.fetchContracts=this.fetchContracts.bind(this);
  }

  fetchContracts(){
    this.props.fetchMethod((data:ListResponse)=> {
      this.setState({response:data})
    })
  }

  componentDidUpdate(){
    this.fetchContracts();
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
          <Listing onSelect={this.handleClick} addNewRoute='/contracts/new' fetchModels={this.fetchContracts} response={this.state.response} modelName='contract' columns={['id','type','vehicle']}/>
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
