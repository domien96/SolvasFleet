import React from 'react';

import Header     from '../app/Header.tsx';
import ClientForm from './ClientForm.tsx'

import { postClient } from '../../actions/client_actions.ts';
import { hasError }  from '../../utils/utils.ts';
import { redirect_to } from'../../routes/router.tsx';

class AddClient extends React.Component<{}, Company.CForm.State> {

  constructor() {
    super();
    this.state = {
      errors: [],
      company: { address: {} }
    };
    this.handleChange = this.handleChange.bind(this);
    this.onSubmit     = this.onSubmit.bind(this);
  }

  public handleChange(field : Company.Field, isAddress : boolean, e : any) : void {
    var newClient : Company = this.state.company;
    if(isAddress){
      newClient['address'][field] = e.target.value;
    }
    else{
      newClient[field] = e.target.value;
    }
    this.setState({ company: newClient });
  }

  public onSubmit(e : any) : void {
    e.preventDefault();
    let setErrors = (e : Form.Error[]) => this.setState({ errors: e });
    let success = (data : any) => redirect_to(`/clients/${data.id}`);
    let fail = (data : any) => {
      setErrors(data.errors.map(function(e : any) {
        return { field: e, error: 'null' };
      }));
    }

    postClient(this.state.company, success, fail);
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
