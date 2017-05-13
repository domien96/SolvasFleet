import React from 'react';

import Header from '../app/Header.tsx';
import ContractForm from './ContractForm.tsx';

import { fetchContract, putContract, fetchTypes } from '../../actions/contract_actions.ts';
import { hasError } from '../../utils/utils.ts';
import { redirect_to } from '../../routes/router.tsx';
import T from 'i18n-react';
import Errors from '../../modules/Errors.ts';

interface Props {
  params: { contractId: number };
  fetchContracts: () => void;
}

interface State {
  errors: Form.Error[];
  contract: ContractData;
  types: string[];
}

class EditContract extends React.Component<Props, State> {

  constructor() {
    super();
    this.state = {
      contract: {},
      errors: [],
      types: [],
    };
    this.handleChange = this.handleChange.bind(this);
    this.onSubmit = this.onSubmit.bind(this);
  }

  public handleChange(field: Contract.Field, e: any, type: string): any {
    const contract: ContractData = this.state.contract;
    if (type === 'date') {
      contract[field] = e;
    } else {
      contract[field] = e.target.value;
    }
    this.setState({ contract });
  }

  componentDidMount() {
    fetchContract(this.props.params.contractId, (data: any) => this.setState({ contract: data }));
    fetchTypes((data: any) => this.setState({ types: data.data }));
  }

  public onSubmit(e: any): void {
    e.preventDefault();
    const setErrors = (es: Form.Error[]) => this.setState({ errors: es });
    const success = (data: any) => redirect_to(`/contracts/${data.id}`);

    putContract(this.props.params.contractId, this.state.contract, success, Errors.handle(setErrors));
  }

  render() {
    return (
      <div>
        <Header>
          <h2>{ T.translate('contract.edit') }</h2>
        </Header>
        <ContractForm
          contract={ this.state.contract }
          types={ this.state.types }
          onSubmit={ this.onSubmit }
          handleChange={ this.handleChange }
          hasError={ hasError.bind(this) }
          errors={ this.state.errors }
        />
      </div>
    );
  }
}

export default EditContract;
