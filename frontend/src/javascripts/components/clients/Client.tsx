import React from 'react';
import { browserHistory } from'react-router';

import fetchClient from '../../actions/fetch_company.ts';
import deleteClient from '../../actions/delete_company.ts';

class Client extends React.Component<Company.Props, Company.State> {

  constructor() {
    super();
    this.state = { company : {} };
    this.deleteClient = this.deleteClient.bind(this);
  }

  componentDidMount() {
    fetchClient(this.props.params.id)
      .then((data : any) => {
        this.setState({ company: data })
      });
  }

  public deleteClient(){
    deleteClient(this.props.params.id);
    browserHistory.push('/clients');
  }

  render() {
    var { name, vat_number, phone_number, address } = this.state.company;

    return (
      <div className='card-content Client'>
        <span className='pull-right'>
          <button className='btn btn-default'>
            <span className='glyphicon glyphicon-edit' />
          </button>
          <button onClick = { this.deleteClient } className='btn btn-default'>
            <span className='glyphicon glyphicon-remove' />
          </button>
        </span>
        <h2>{ name }</h2>
        <h5>Information</h5>
        <div>vat_number: { vat_number }</div>
        <div>phone_number: { phone_number }</div>
        <div>address: { address }</div>
      </div>
    );
  }
}

export default Client;
