import React    from 'react';

import Actions from '../forms/Actions.tsx';
import Errors from '../app/Errors.tsx';
import Info from './form/Info.tsx';

interface Props {
  onSubmit     : (e : any) => void;
  handleChange : (field : Contract.Field, e : any, type : string) => void;
  errors       : Form.Error[];
  hasError     : (field : Contract.Field) => boolean;
  contract     : ContractData;
  types        : string[];
}

const ContractForm : React.StatelessComponent<Props> = props => {
  var { contract, onSubmit, handleChange, errors, hasError, types } = props;

  const submit = contract.id != null ? 'form.update' : 'form.create';

  return (
    <form method='post' onSubmit={ onSubmit } >
      <div className='wrapper'>
        <div className='row'>
          <Errors errors={ errors } />
          <Info contract={ contract } handleChange={ handleChange } hasError={ hasError } types={ types }/>
          <div className='col-xs-12 col-md-5'>
            <div className='row'>
              <Actions submitLabel={ submit } cancelUrl={ `/contracts/${contract.id || ''}` } model='contract' />
            </div>
          </div>
        </div>
      </div>
    </form>
  );
}

export default ContractForm;
