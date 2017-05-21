import React from 'react';
import { Link } from 'react-router';

import Card from '../app/Card.tsx';
import Header from '../app/Header.tsx';
import DetailTable from '../tables/DetailTable.tsx';
import Fleets from '../fleets/Fleets.tsx';
import Contracts from '../contracts/Contracts.tsx';
import LogLink from '../app/LogLink.tsx';

import { fetchFleets } from '../../actions/fleet_actions.ts';
import { callback } from '../../actions/fetch_json.ts';
import { fetchClient, deleteClient, putClient } from '../../actions/client_actions.ts';
import { redirect_to } from '../../routes/router.tsx';
import Confirm from 'react-confirm-bootstrap';
import { th } from '../../utils/utils.ts';
import { fetchContractsForCompany, fetchContracts } from '../../actions/contract_actions.ts';

interface Props {
  [ params: string ]: { [ id: string ]: number };
}

interface State {
  company: CompanyData;
  fleets: FleetData[];
}

class Client extends React.Component<Props, State> {

  constructor() {
    super();
    this.state = { company: { address: {} }, fleets: [] };
    this.state.company.type = 'Customer';
    this.deleteClient = this.deleteClient.bind(this);
    this.unarchiveClient = this.unarchiveClient.bind(this);
    this.fetchContracts = this.fetchContracts.bind(this);
  }

  componentDidMount() {
    fetchClient(this.props.params.id, (data: any) => {
      this.setState({ company: data });
    });

    fetchFleets(this.props.params.id, (data: any) => {
      this.setState({ fleets: data.data });
    });
  }

  deleteClient() {
    deleteClient(this.props.params.id, () => redirect_to('/clients'));
  }

  unarchiveClient() {
    const success = () => redirect_to(`/clients/${this.state.company.id}`);
    this.state.company['archived'] = false;
    putClient(this.state.company.id, this.state.company, success);
  }

  fetchContracts(params: ContractParams, success?: callback, fail?: callback) {
    if (this.state.company.type === "InsuranceCompany") {
      fetchContracts(success, fail, { InsuranceCompany: params.companyId });
    } else {
      fetchContractsForCompany(params.companyId, success, fail);
    }
  }

  render() {
    const { name, vatNumber, phoneNumber, address, type, archived } = this.state.company;
    const { street, houseNumber, city, postalCode, country } = address;
    const id = this.props.params.id;

    const data = [
      th('company.vatNumber', vatNumber),
      th('company.phoneNumber', phoneNumber),
      th('company.types', type),
      th('company.address.street', street),
      th('company.address.houseNumber', houseNumber),
      th('company.address.postalCode', postalCode),
      th('company.address.city', city),
      th('company.address.country', country),
    ];

  let deleteLink = (
    <Confirm
      onConfirm={ this.deleteClient }
      body="Are you sure you want to archive this?"
      confirmText="Confirm Archive"
      title="Archive client">
      <button className='btn btn-danger form-control'>
        <span className='glyphicon glyphicon-remove' /> Archive
      </button>
    </Confirm>
    );
  if (archived) {
    deleteLink = (
      <Confirm
        onConfirm={ this.unarchiveClient }
        body="Are you sure you want to restore this?"
        confirmText="Confirm Unarchive"
        title="Unarchive company">
        <button className='btn btn-success form-control'>
          <span className='glyphicon glyphicon-share-alt' /> Unarchive
        </button>
      </Confirm>
    );
  }

    return (
      <div>
        <Header>
          <h2>{ name }</h2>
        </Header>
        <div className='wrapper'>
          <div className='row'>
            <div className='col-xs-12 col-md-6'>
              <Card>
                <div className='card-content'>
                  <div className='row actions'>
                    <div className='col-sm-3'>
                      <Link to={ '/clients/' + id + '/edit' } className='btn btn-default form-control'>
                        <span className='glyphicon glyphicon-edit' /> Edit
                      </Link>
                    </div>
                    <div className='col-sm-3'>
                      <Link to={ `/commissions/clients/${id}` } className='btn btn-info form-control'>
                        <span className='glyphicon glyphicon-euro' /> Commissions
                      </Link>
                    </div>
                    <div className='col-sm-3'>
                      { deleteLink }
                    </div>
                    <LogLink id={ id } type='Company' />
                  </div>
                </div>
              </Card>
              <Card>
                <div className='card-content'>
                  <DetailTable data={ data }/>
                </div>
              </Card>
            </div>
            <div className='col-xs-12 col-md-6'>
              <Fleets fleets={ this.state.fleets } company={ this.props.params.id } />
              <Contracts
                companyId={ this.props.params.id }
                vehicleId={ null }
                fleetId={ null }
                fetchMethod={ this.fetchContracts } />
              </div>
          </div>
        </div>
      </div>
    );
  }
}

export default Client;
