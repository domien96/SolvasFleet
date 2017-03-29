import React from 'react';
import { browserHistory } from 'react-router';

import Header from '../app/Header.tsx';
import ClientForm from './ClientForm.tsx';

import { fetchClient, putClient }    from '../../actions/client_actions.ts';
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
    fetchClient(this.props.params.id, (data : any) => {
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
    let success = () => browserHistory.push(`/clients/${this.state.company.id}`);
    let fail = (data : any) => {
      setErrors(data.errors.map(function(e : any) {
        return { field: e.field, error: 'null' };
      }));
    }

    putClient(this.state.company.id, success, fail);
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
