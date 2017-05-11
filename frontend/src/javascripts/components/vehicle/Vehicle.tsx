import React from 'react';

import { fetchVehicle, deleteVehicle, fetchGreencardPdf }  from '../../actions/vehicle_actions.ts';
import { redirect_to } from'../../routes/router.tsx';
import { fetchContracts} from '../../actions/contract_actions.ts';
import FileSaver from 'file-saver';
import VehicleView from './VehicleView.tsx'
import Contracts from '../contracts/Contracts.tsx'
import { callback } from '../../actions/fetch_json.ts';

interface Props {
  params: { id: number };
  fetchVehicles: () => void;
}

interface State {
  vehicle: VehicleData;
}

class Vehicle extends React.Component<Props, State> {

  constructor() {
    super();
    this.state = { vehicle: { type: 'PersonalVehicle' } };
    this.deleteVehicle = this.deleteVehicle.bind(this);
    this.fetchContracts = this.fetchContracts.bind(this);
    this.handleDownloadGreencard = this.handleDownloadGreencard.bind(this);
  }

  fetchVehicle(id: number) {
    fetchVehicle(id, ((data: any) => {
      this.setState({ vehicle: data });
    }));
  }

  componentDidMount() {
    this.fetchVehicle(this.props.params.id);
  }

  componentWillReceiveProps(nextProps: Props) {
    if (nextProps.params.id !== this.props.params.id) {
      this.fetchVehicle(nextProps.params.id);
    }
  }

  deleteVehicle() {
    const reloadVehicles = this.props.fetchVehicles;
    deleteVehicle(this.props.params.id, reloadVehicles);
    redirect_to('/vehicles');
  }

  fetchContracts(params: ContractParams, success?: callback, fail?: callback) {
    fetchContracts(success, fail, { vehicle: params.vehicleId });
  }

  handleDownloadGreencard(){
    var { id } = this.props.params;
    let fail = (data : any) => console.log(data);
    fetchGreencardPdf(id, ((data : any) => {
      FileSaver.saveAs(data, `greencard_${id}.${'pdf'}`);
    }), fail);
  }

  render() {
    return(
      <div>
        <VehicleView 
          vehicle={ this.state.vehicle } 
          handleDelete={ this.deleteVehicle }
          onDownloadGreencard={ this.handleDownloadGreencard } />
        <Contracts
          vehicleId={ this.props.params.id }
          companyId={ null }
          fleetId={ null }
          fetchMethod={ this.fetchContracts } />
      </div>
    );
  }
}

export default Vehicle;
