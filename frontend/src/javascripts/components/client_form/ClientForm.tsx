import React    from 'react';

import Errors     from '../app/Errors.tsx';
import Actions from '../forms/Actions.tsx';

import Info from './form/Info.tsx';

interface Props {
  onSubmit     : (e : any) => void;
  handleChange : (field : Company.Field, isAddress : boolean, e : any) => void;
  errors       : Form.Error[];
  hasError     : (field : Company.Field) => boolean;
  company      : CompanyData;
}

const ClientForm : React.StatelessComponent<Props> = props => {
  const submit = props.company.id != null ? 'form.update' : 'form.create';

  return (
    <form method='post' onSubmit={ props.onSubmit } >
      <div className='wrapper'>
        <div className='row'>
          <Errors errors={ props.errors } />
          <Info company={ props.company } handleChange={ props.handleChange } hasError={ props.hasError }/>
          <div className='col-xs-12 col-md-5'>
            <div className='row'>
              <Actions submitLabel={ submit } cancelUrl={ `/clients/${props.company.id || ''}` } model='client' />
            </div>
          </div>
        </div>
      </div>
    </form>
  );
}

export default ClientForm;
