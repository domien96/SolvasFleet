import React from 'react';

import Header     from '../app/Header.tsx';
import ContractForm   from './ContractForm.tsx';

import { postContract } from '../../actions/contract_actions.ts';
import { hasError } from '../../utils/utils.ts';
import { redirect_to } from'../../router.tsx';

interface State {
  errors : Form.Error[];
  contract   : ContractData;
}

class AddContract extends React.Component<{}, State> {

	constructor() {
    super();
    this.state = {
      errors: [],
      contract: {}
    };
    this.handleChange = this.handleChange.bind(this);
    this.onSubmit     = this.onSubmit.bind(this);
  }

  public handleChange(field : Contract.Field, e : any, type : string) : any {
    var contract : ContractData = this.state.contract;
    if(type == 'date'){
    	contract[field] = e;
    }
    else{
    	contract[field] = e.target.value;
		}
    this.setState({ contract });
  }

  public onSubmit(e : any) : void {
    e.preventDefault();
    let setErrors = (e : Form.Error[]) => this.setState({ errors: e });
    let success = (data : any) => redirect_to(`/contracts/${data.id}`);
    let fail = (data : any) => {
      setErrors(data.errors.map(function(e : any) {
        return { field: e, error: 'null' };
      }));
    }

    postContract(this.state.contract, success, fail);
  }

  render() {
    return (
      <div>
        <Header>
          <h2>Add A New Contract</h2>
        </Header>
        <ContractForm
          contract={ this.state.contract }
          onSubmit={ this.onSubmit }
          handleChange={ this.handleChange }
          hasError={ hasError.bind(this) }
          errors={ this.state.errors }
        />
      </div>
    );
  }
}

export default AddContract;
