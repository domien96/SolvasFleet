import React from 'react';

import { fetchContract } from '../../actions/contract_actions.ts';

import ContractView from './ContractView.tsx'

interface Props {
   params : { id : number };
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

  fetchContract(id : number){
  	fetchContract(id, ((data) => {
      this.setState({ contract: data })
    }));
  }

  ComponentDidMount(){
  	fetchContract(this.props.params.id);
  }

  render(){
  	return(
  		<ContractView contract={ this.state.contract }/>
  	);
  }


}

export default Contract;