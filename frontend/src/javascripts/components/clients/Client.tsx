import React from 'react';
import { browserHistory, Link } from'react-router';

import fetchClient  from '../../actions/fetch_company.ts';
import fetchFleets  from '../../actions/fetch_fleets_by_company.ts';
import deleteClient from '../../actions/delete_company.ts';
import Card         from '../app/Card.tsx';
import Header       from '../app/Header.tsx';
import DetailTable  from '../tables/DetailTable.tsx';
import FleetForm    from '../fleets/FleetForm.tsx';

import { th } from '../../utils/utils.ts';

interface FleetsProps {
  fleets : Fleet[];
}
interface FleetsState {
  formVisible : boolean
}
class Fleets extends React.Component<FleetsProps, FleetsState> {
  constructor(props : FleetsProps) {
    super(props);
    this.state = { formVisible: false };
  }

  onClick() {
    this.setState({ formVisible: true })
  }

  render() {
    let fleets = this.props.fleets.map((f, i) => {
      return (
        <Link to={ '/fleets/' + f.id } key={ i } className='fleet'>
          <h3>{ f.name }</h3>
          <div className='actions pull-right'>
            <h3>
              <span className='glyphicon glyphicon-menu-right' />
            </h3>
          </div>
        </Link>
      )
    });

    return (
      <Card>
        <div className='card-title'>
          <h2>Fleets</h2>
          <span onClick={ this.onClick.bind(this) }>Click me</span>
        </div>
        <div className='card-content fleets'>
          <div className='fleet-form-wrapper'>
            <FleetForm />
          </div>
          { fleets }
        </div>
      </Card>
    )};
}
class Client extends React.Component<Company.Props, Company.State> {

  constructor() {
    super();
    this.state = { company : { address: {} }, fleets : [] };
    this.deleteClient = this.deleteClient.bind(this);
  }

  componentDidMount() {
    fetchClient(this.props.params.id)
      .then((data : any) => {
        this.setState({ company: data })
      });
    fetchFleets(this.props.params.id)
      .then((data : any) => {
        this.setState({ fleets: data.data })
      });
  }

  public deleteClient(){
    deleteClient(this.props.params.id).then(function(this: any) {
      browserHistory.push('/clients');
    });
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
                <div className='col-sm-6'>
                  <div className='card-content'>
                    <DetailTable data={ data }/>
                  </div>
                </div>
              </Card>
            </div>
            <div className='col-xs-12 col-md-6'>
              <Fleets fleets={ this.state.fleets } />
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default Client;
