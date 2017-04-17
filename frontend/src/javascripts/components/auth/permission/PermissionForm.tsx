import React    from 'react';

import Actions from '../../forms/Actions.tsx';
import Errors from '../../app/Errors.tsx';
import Info from './form/Info.tsx';

interface Props {
  onSubmit     : (e : any) => void;
  handleChange : (field : Permission.Field, e : any) => void;
  errors       : Form.Error[];
  hasError     : (field : Permission.Field) => boolean;
  permission   : PermissionData;
}

const PermissionForm : React.StatelessComponent<Props> = props => {
  var { permission, onSubmit, handleChange, errors, hasError } = props;

  const submit = permission.id != null ? 'form.update' : 'form.create';

  return (
    <form method='post' onSubmit={ onSubmit } >
      <div className='wrapper'>
        <div className='row'>
          <Errors errors={ errors } />
          <Info permission={ permission } handleChange={ handleChange } hasError={ hasError } />
          <div className='col-xs-12 col-md-5'>
            <div className='row'>
              <Actions submitLabel={ submit } cancelUrl={ `/permissions/${permission.id || ''}` } model='permission' />
            </div>
          </div>
        </div>
      </div>
    </form>
  );
}

export default PermissionForm;
