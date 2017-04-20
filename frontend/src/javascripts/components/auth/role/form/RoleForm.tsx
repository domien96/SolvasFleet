import React    from 'react';

import Actions from '../../../forms/Actions.tsx';
import Errors from '../../../app/Errors.tsx';
import Info from './Info.tsx';

interface Props {
  onSubmit     : (e : any) => void;
  handleChange : (field : Role.Field, e : any) => void;
  errors       : Form.Error[];
  hasError     : (field : Role.Field) => boolean;
  role         : RoleData;
  permissions  : string[];
}

const RoleForm : React.StatelessComponent<Props> = props => {
  var { role, permissions, onSubmit, handleChange, errors, hasError } = props;

  const submit = role.id != null ? 'form.update' : 'form.create';

  return (
    <form method='post' onSubmit={ onSubmit } >
      <div className='wrapper'>
        <div className='row'>
          <Errors errors={ errors } />
          <Info role={ role } permissions={ permissions.sort() } handleChange={ handleChange } hasError={ hasError } />
          <div className='col-xs-12 col-md-5'>
            <div className='row'>
              <Actions submitLabel={ submit } cancelUrl='/auth' model='role' />
            </div>
          </div>
        </div>
      </div>
    </form>
  );
}

export default RoleForm;
