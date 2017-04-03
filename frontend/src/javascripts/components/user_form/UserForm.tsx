import React    from 'react';

import Actions from '../forms/Actions.tsx';
import Errors from '../app/Errors.tsx';
import Info from './form/Info.tsx';
import Permissions from './form/Permissions.tsx';

interface Props {
  onSubmit     : (e : any) => void;
  handleChange : (field : User.Field, e : any) => void;
  errors       : Form.Error[];
  hasError     : (field : User.Field) => boolean;
  user         : UserData;
}

const UserForm : React.StatelessComponent<Props> = props => {
  var { user, onSubmit, handleChange, errors, hasError, user } = props;

  const submit = user.id != null ? 'form.update' : 'form.create';

  return (
    <form method='post' onSubmit={ onSubmit } >
      <div className='wrapper'>
        <div className='row'>
          <Errors errors={ errors } />
          <Info user={ user } handleChange={ handleChange } hasError={ hasError } />
          <div className='col-xs-12 col-md-5'>
            <div className='row'>
              <Permissions />
              <Actions submitLabel={ submit } cancelUrl={ `/users/${user.id || ''}` } model='user' />
            </div>
          </div>
        </div>
      </div>
    </form>
  );
}

export default UserForm;
