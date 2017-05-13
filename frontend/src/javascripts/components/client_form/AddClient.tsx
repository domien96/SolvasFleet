import React from 'react';

import Header from '../app/Header.tsx';
import ClientForm from './ClientForm.tsx';
import T from 'i18n-react';

import { postClient } from '../../actions/client_actions.ts';
import { hasError } from '../../utils/utils.ts';
import { redirect_to } from '../../routes/router.tsx';
import Errors from '../../modules/Errors.ts';

interface State {
  errors: Form.Error[];
  company: CompanyData;
}
class AddClient extends React.Component<{}, State> {

  constructor() {
    super();
    this.state = {
      company: { address: {} },
      errors: [],
    };
    this.state.company.type = 'Customer';
    this.handleChange = this.handleChange.bind(this);
    this.onSubmit     = this.onSubmit.bind(this);
  }

  public handleChange(field: Company.Field, isAddress: boolean, e: any): void {
    const newClient: CompanyData = this.state.company;
    if (isAddress) {
      newClient.address[field] = e.target.value;
    } else {
      newClient[field] = e.target.value;
    }
    this.setState({ company: newClient });
  }

  public onSubmit(e: any): void {
    e.preventDefault();
    const setErrors = (es: Form.Error[]) => this.setState({ errors: es });
    const success = (data: any) => redirect_to(`/clients/${data.id}`);
    postClient(this.state.company, success, Errors.handle(setErrors));
  }

  render() {
    return (
      <div>
        <Header>
          <h2>{ T.translate('company.addNew') }</h2>
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
