import React from 'react';

import { fetchContract } from '../../actions/contract_actions.ts';

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
    //this.deleteContract = this.deleteContract.bind(this);
  }

  fetchContract(contractId : number){
  	fetchContract(contractId, ((data) => {
      this.setState({ contract: data })
    }));
  }

  ComponentDidMount(){
  	fetchContract(this.props.params.contractId);
  }

  render(){
  	return(
  		<ContractView contract={ this.state.contract }/>
  	);
  }
}

export default Contract;