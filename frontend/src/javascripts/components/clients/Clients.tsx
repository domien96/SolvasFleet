import React from 'react';
import { browserHistory, Link } from'react-router';

import Card       from '../app/Card.tsx';
import Header     from '../app/Header.tsx';

import { InfoTable, th }  from '../tables/InfoTable.tsx';

import fetchClients from '../../actions/fetch_companies.ts';


interface OverviewProps {
  companies: Company[];
}

class Overview extends React.Component<OverviewProps, {}> {

  constructor() {
    super();
    this.handleClick = this.handleClick.bind(this);
  }

  public handleClick(id : number) {
    browserHistory.push('/clients/' + id);
  }

  render() {
    const tableHead = [
      th('id', 'company.id'),
      th('name', 'company.name'),
      th('vatNumber', 'company.vatNumber'),
      th('phoneNumber', 'company.phoneNumber'),
      th('address', 'company.address')
    ];

    return (
      <InfoTable head={ tableHead } data={ this.props.companies } onClick={ this.handleClick } />
    );
  }
}


class Clients extends React.Component<{}, Companies.State> {

  constructor(props : {}) {
    super(props);
    this.state = { companies: [] };
  }

  componentDidMount() {
    fetchClients()
      .then((data : Companies.Data) => {
        this.setState({ companies: data.companies })
      });
  }

  render() {
    return (
      <div>
        <Header>
          <h2>Clients</h2>
        </Header>
        <div className='wrapper'>
          <div className='row'>
            <div className='col-xs-12 col-md-12'>
              <Card>
                <div className='card-content'>
                  <Link to='/clients/new' className='btn btn-default pull-right'>
                    <span className='glyphicon glyphicon-plus' aria-hidden='true'></span>
                    Add new client
                  </Link>
                  <Overview companies={ this.state.companies } />
                </div>
              </Card>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default Clients;
