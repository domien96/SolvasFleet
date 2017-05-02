import React from 'react';

import { fetchContract, deleteContract } from '../../actions/contract_actions.ts';
import { redirect_to } from'../../routes/router.tsx';


import ContractView from './ContractView.tsx'

interface Props {
   params : { vehicleId : number, contractId : number };
}

interface State {
  contract : ContractData;
}

class Contract extends React.Component<Props, State> {

  constructor() {
    super();
    this.state = { contract: {} };
    this.handleDelete = this.handleDelete.bind(this);
  }

  fetchContract(contractId : number){
  	fetchContract(contractId, ((data) => {
      this.setState({ contract: data })
    }));
  }

  componentDidMount(){
  	this.fetchContract(this.props.params.contractId);
  }

  handleDelete(){
    deleteContract(this.props.params.contractId, () => redirect_to('/contracts'));
  }

  render(){
  	return(
  		<ContractView contract={ this.state.contract } handleDelete={ this.handleDelete } />
  	);
  }
}

export default Contract;
