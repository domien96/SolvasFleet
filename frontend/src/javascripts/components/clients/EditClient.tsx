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
      errors: [],
      company: {}
    };
    this.state.company.address = {};
    this.handleChange = this.handleChange.bind(this);
    this.onSubmit     = this.onSubmit.bind(this);
  }

  componentDidMount() {
    fetchClient(this.props.params.id)
      .then((data : any) => {
        this.setState({ company: data })
      });
  }

  handleChange(field : Company.Field, isAddress : boolean, e : any) : any {
    var newClient : Company = this.state.company;
    if(isAddress){
      newClient['address'][field] = e.target.value;
    }
    else{
      newClient[field] = e.target.value;
    }
    this.setState({ company: newClient });
  }

  onSubmit(e : any) : void {
    e.preventDefault();
    let setErrors = (e : Form.Error[]) => this.setState({ errors: e });

    fetch(COMPANIES_URL + '/' + this.state.company.id, {
      method: 'PUT',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(this.state.company)
    })
    .then(function(response) {
      return response.json().then(function(data) {
        if (response.ok) {
          browserHistory.push('/clients/' + data.id);
        } else {
          setErrors(data.errors.map(function(e : any) {
            return { field: e.field, error: 'null' };
          }));
        }
      });
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
