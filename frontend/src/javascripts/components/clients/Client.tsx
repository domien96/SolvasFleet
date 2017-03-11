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
    deleteClient(this.props.params.id).then(function(this: any) {
      browserHistory.push('/clients');
    });
  }

  render() {
    var { name, vat_number, phone_number, address } = this.state.company;

    return (
      <div className='card-content user'>
        <h2>{ name }</h2>
        <div className='col-sm-4'>
          <div className='row actions'>
            <div className='col-sm-6'>
              <button className='btn btn-default form-control'>
                <span className='glyphicon glyphicon-edit' />
                Edit
              </button>
            </div>
            <div className='col-sm-6'>
              <button onClick = { this.deleteClient } className='btn btn-danger form-control'>
                <span className='glyphicon glyphicon-remove' />
                Delete
              </button>
            </div>
          </div>
        </div>
        <div className='col-sm-12'>
          <h5>Information</h5>
          <div>vat_number: { vat_number }</div>
          <div>phone_number: { phone_number }</div>
          <div>address: { address }</div>
        </div>
      </div>
    );
  }
}

export default Client;
