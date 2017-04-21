import React from 'react';

import { redirect_to } from'../../routes/router.tsx';
import Listing from '../app/Listing.tsx';

interface Props {
  fetchMethod: any //succes,fail
  vehicleId: number
  fleetId: number
  companyId: number
}

interface State {
  response: ListResponse;
  isMounted:boolean

}

class Contracts extends React.Component<Props, State> {

	constructor(props : any) {
    super(props);
    this.state = {  response:{total:0,first : "", last : "", limit : 0, offset : 0, previous : "", next : "",data:[]},isMounted:false};
    this.fetchContracts=this.fetchContracts.bind(this);
    this.fetchContractsWithProps=this.fetchContractsWithProps.bind(this);
  }

  fetchContracts(vehicleId:number,companyId:number,fleetId:number){
    if(this.state.isMounted){
    this.props.fetchMethod(vehicleId,companyId,fleetId,(data:ListResponse)=> {
      this.setState({response:data})
    })}
  }

  fetchContractsWithProps(){
    this.fetchContracts(this.props.vehicleId,this.props.companyId,this.props.fleetId);
  }

  componentWillReceiveProps(nextProps:any) {
    this.fetchContracts(nextProps.vehicleId,nextProps.companyId,nextProps.fleetId);
  }




  handleClick(id : number) {
    redirect_to(`/contracts/${id}`);
  }



  componentDidMount() {
    this.setState({isMounted:true})
    this.fetchContractsWithProps();
  }

  render(){
	  	return(
          <Listing onSelect={this.handleClick} addNewRoute='/contracts/new' fetchModels={this.fetchContractsWithProps} response={this.state.response} modelName='contract' columns={['id','type','vehicle']}/>
	  	);

  }
}

export default Contracts;
