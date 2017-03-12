import React from 'react';
import { COMPANIES_URL } from '../../constants/constants.ts';
import { browserHistory } from 'react-router';

import Header from '../app/Header.tsx';
import ClientForm from './ClientForm.tsx';

import fetchClient    from '../../actions/fetch_company.ts';
import { hasError } from '../../utils/utils.ts';

class EditClient extends React.Component<Company.Props, Company.CForm.State> {
  constructor() {
    super();
    this.state = {
      errors: [ { field: 'name', error: 'null' }],
      company: {}
    };
    this.handleChange = this.handleChange.bind(this);
    this.onSubmit     = this.onSubmit.bind(this);
  }

  componentDidMount() {
    fetchClient(this.props.params.id)
      .then((data : any) => {
        console.log('fetched');
        console.log(data);
        this.setState({ company: data })
      });
  }

  handleChange(field : Company.Field, e : any) : any {
    var company : Company = this.state.company;
    company[field] = e.target.value;
    this.setState({ company });
  }

  onSubmit(e : any) : void {
    e.preventDefault();
    fetch(COMPANIES_URL + '/' + this.state.company.id, {
      method: 'PUT',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(this.state.company)
    })
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
          <h2>Edit Client</h2>
        </Header>
        <ClientForm
          company={ this.state.company }
          onSubmit={ this.onSubmit }
          handleChange={ this.handleChange }
          hasError={ hasError.bind(this) }
          errors={ this.state.errors }
          />
      </div>
    )
  }
}

export default EditClient;
