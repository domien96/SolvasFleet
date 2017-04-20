import React from 'react';
import { Link } from'react-router';

import Card         from '../app/Card.tsx';
import Header       from '../app/Header.tsx';
import DetailTable  from '../tables/DetailTable.tsx';
import Fleets       from '../fleets/Fleets.tsx';
import Contracts    from '../contracts/Contracts.tsx'

import { fetchFleets } from '../../actions/fleet_actions.ts';
import { callback } from '../../actions/fetch_json.ts';
import { fetchClient, deleteClient } from '../../actions/client_actions.ts';
import { redirect_to } from'../../routes/router.tsx';

import { th } from '../../utils/utils.ts';

import { fetchContractsByCompany} from '../../actions/contract_actions.ts';

interface Props {
  [ params : string ] : { [ id : string ] : number };
}

interface State {
  company : Company;
  fleets : FleetData[];
}

class Client extends React.Component<Props, State> {

  constructor() {
    super();
    this.state = { company : { address: {} }, fleets : [] };
    this.deleteClient = this.deleteClient.bind(this);
    this.fetchContracts= this.fetchContracts.bind(this);
  }

  componentDidMount() {
    fetchClient(this.props.params.id, (data : any) => {
      this.setState({ company: data })
    });
    fetchFleets(this.props.params.id, (data : any) => {
      this.setState({ fleets: data.data })
    });
  }

  public deleteClient(){
    deleteClient(this.props.params.id, () => redirect_to('/clients'));
  }

  fetchContracts(vehicleId:number,companyId:number,fleetId:number,success?:callback,fail?:callback) {
    fetchContractsByCompany(companyId,success,fail);
  }

  render() {
    var { name, vatNumber, phoneNumber, address } = this.state.company;
    var { street, houseNumber, city, postalCode, country } = address;

    var id = this.props.params.id;

    const data = [
      th('company.vatNumber', vatNumber),
      th('company.phoneNumber', phoneNumber),
      th('company.address.street', street),
      th('company.address.houseNumber', houseNumber),
      th('company.address.postalCode', postalCode),
      th('company.address.city', city),
      th('company.address.country', country)
    ];


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
                    <div className='col-sm-6'>
                      <Link to={ '/clients/' + id + '/edit' } className='btn btn-default form-control'>
                        <span className='glyphicon glyphicon-edit' /> Edit
                      </Link>
                    </div>
                    <div className='col-sm-6'>
                      <button onClick = { this.deleteClient } className='btn btn-danger form-control'>
                        <span className='glyphicon glyphicon-remove' /> Delete
                      </button>
                    </div>
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
              <Contracts companyId={ this.props.params.id } vehicleId={null} fleetId={null} fetchMethod={this.fetchContracts}/>
              </div>
          </div>
        </div>
      </div>
    );
  }
}

export default Client;
