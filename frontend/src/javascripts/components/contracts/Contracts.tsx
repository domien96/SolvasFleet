import React from 'react';

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
}

class Contracts extends React.Component<Props, State> {

	constructor(props : any) {
    super(props);
    this.state = {  response:{total:0,first : "", last : "", limit : 0, offset : 0, previous : "", next : "",data:[]}};
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
  	if(this.state.response.total != 0){
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
