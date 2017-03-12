import React from 'react';
import { browserHistory } from 'react-router';

import Header     from '../app/Header.tsx';
import ClientForm from './ClientForm.tsx'


import createCompany from '../../actions/create_company.ts';
import { hasError }  from '../../utils/utils.ts';


class AddClient extends React.Component<{}, Company.CForm.State> {

  constructor() {
    super();
    this.state = {
      errors: [ { field: 'name', error: 'null' }],
      company: {}
    };
    this.handleChange = this.handleChange.bind(this);
    this.onSubmit     = this.onSubmit.bind(this);
  }

  public handleChange(field : Company.Field, e : any) : void {
    var newClient : Company = this.state.company;
    newClient[field] = e.target.value;
    this.setState({ company: newClient });
  }

  public onSubmit(e : any) : void {
    e.preventDefault();

    createCompany(this.state.company)
    .then(function(response) {
      return response.json()
    })
    .then(() => {
      browserHistory.push('/clients');
    });
  }

  render() {
    return (
      <div>
        <Header>
          <h2>Add A New Client</h2>
        </Header>
        <ClientForm
          company={ this.state.company }
          onSubmit={ this.onSubmit }
          handleChange={ this.handleChange }
          hasError={ hasError.bind(this) }
          errors={ this.state.errors }
          />
      </div>
    );
  }
}

export default AddClient;
