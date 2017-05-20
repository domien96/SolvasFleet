import React from 'react';

import { fetchVehicle, deleteVehicle, fetchGreencardPdf } from '../../actions/vehicle_actions.ts';
import { redirect_to } from'../../routes/router.tsx';
import { fetchContracts} from '../../actions/contract_actions.ts';
import { fetchClients } from '../../actions/client_actions.ts';
import { fetchFleets } from '../../actions/fleet_actions.ts';
import FileSaver from 'file-saver';
import VehicleView from './VehicleView.tsx'
import Contracts from '../contracts/Contracts.tsx'
import { callback } from '../../actions/fetch_json.ts';
import T from 'i18n-react';
import DynamicGuiComponent from '../app/DynamicGuiComponent.tsx';
import Auth from '../../modules/Auth.ts';

interface Props {
  params: { id: number };
  fetchVehicles: () => void;
}

interface State {
  vehicle: VehicleData;
  companyOfFleet: number;
  companies: CompanyData[];
  fleets: FleetData[];
}

class Vehicle extends React.Component<Props, State> {

  constructor() {
    super();
    this.state = {
      vehicle: { type: 'PersonalVehicle' },
      companies: [],
      fleets: [],
      companyOfFleet: 0
    };
    this.deleteVehicle = this.deleteVehicle.bind(this);
    this.fetchContracts = this.fetchContracts.bind(this);
    this.handleDownloadGreencard = this.handleDownloadGreencard.bind(this);
    this.handleGetFleetName = this.handleGetFleetName.bind(this);
    this.handleGetCompanyName = this.handleGetCompanyName.bind(this);
  }

  fetchVehicle(id: number) {
    fetchVehicle(id, ((data: any) => {
      const vehicle: VehicleData = data;
      vehicle['year'] = data.year.split('-')[0];
      this.setState({ vehicle: vehicle });
    }));
  }

  componentDidMount() {
    this.fetchVehicle(this.props.params.id);
    this.fetchClients();
    this.setState({ companyOfFleet: this.getCompanyOfFleetId() });
  }

  componentWillReceiveProps(nextProps: Props) {
    if (nextProps.params.id !== this.props.params.id) {
      this.fetchVehicle(nextProps.params.id);
      this.setState({ companyOfFleet: this.getCompanyOfFleetId() });
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

  fetchClients() {
    fetchClients((data: any) => {
      this.setState({ companies: data.data });
      this.fetchFleets();
    });
  }

  fetchFleets(){
    let allFleets: FleetData[] = []
    if (this.state.companies) {
      this.state.companies.map((company: CompanyData) => {
        fetchFleets(company.id, (data: any) => {
          let fleets: FleetData[] = data.data
          fleets.map((fleet: FleetData) => {
            allFleets.push(fleet);
          })
          this.setState({ fleets: allFleets });
        });
      })
    }
  }

  handleDownloadGreencard() {
    const { id } = this.props.params;
    let fail = (data: any) => console.log(data);
    fetchGreencardPdf(id, ((data: any) => {
      FileSaver.saveAs(data, `greencard_${id}.pdf`);
    }), fail);
  }

  handleGetFleetName(id: number) {
    if (this.state.fleets.length > 0 && id) {
      const fleet = this.state.fleets.filter((f: FleetData) => {
        return (id === f.id);
      });
      return fleet[0].name;
    }
    return id.toString();
  }

  handleGetCompanyName(id: number) {
    if (id === -1) {
      return T.translate('company.allCompanies');
    }
    if (this.state.companies.length > 0) {
      const companiesFiltered = this.state.companies.filter((c: CompanyData) => {
        return c.id === id;
      });
      return companiesFiltered[0].name;
    }
    else {
      return id.toString();
    }
  }

  getCompanyOfFleetId() {
    if (this.state.fleets.length > 0) {
      const fleet = this.state.fleets.filter((f: FleetData) => {
        return (this.state.vehicle.id === f.id);
      });
      return fleet[0].company;
    }
    return 0;
  }

  render() {
    return(
      <div>
        <VehicleView
          vehicle={ this.state.vehicle }
          handleDelete={ this.deleteVehicle }
          onDownloadGreencard={ this.handleDownloadGreencard }
          onGetCompanyName={ this.handleGetCompanyName }
          onGetFleetName={ this.handleGetFleetName }
          companyOfFleet={  this.state.companyOfFleet }
          />
        <DynamicGuiComponent authorized={ Auth.canReadContractsOfCompany(this.state.companyOfFleet) }>
          <Contracts
            vehicleId={ this.props.params.id }
            companyId={ null }
            fleetId={ null }
            fetchMethod={ this.fetchContracts } />
        </DynamicGuiComponent>
      </div>
    );
  }
}

export default Vehicle;
