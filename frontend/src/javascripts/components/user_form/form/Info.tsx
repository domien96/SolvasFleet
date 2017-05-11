import React from 'react';

import FormField from '../../forms/FormField.tsx';
import Card from '../../app/Card.tsx';

export interface Props {
  handleChange: (field: string, e: any) => void;
  hasError: (e: any) => boolean;
  user: UserData;
}

const Info: React.StatelessComponent<Props> = (props) => {
  const handleChange = (field: User.Field) => {
    return (e: any) => {
      props.handleChange(field, e);
    };
  };

  const { id, firstName, lastName, email, password } = props.user;
  let pwmsg;
  if (id != null) {
    pwmsg = (
      <div className='col-xs-12'>
        <div className='panel panel-info'>
          <div className='panel-body'>
            It is not required to edit the password to make other changes to the user.
          </div>
        </div>
      </div>
      );
  }

  return (
    <div className='col-xs-12 col-md-7'>
      <Card>
        <div className='card-title'>
          <h5>General info</h5>
        </div>
        <div className='card-content'>
          <FormField
            value={ firstName }
            placeholder='user.firstName'
            type='text'
            callback={ handleChange('firstName') }
            hasError={ props.hasError('firstName')} />
          <FormField
            value={ lastName }
            placeholder='user.lastName'
            type='text'
            callback={ handleChange('lastName') }
            hasError={ props.hasError('lastName')}  />
          <FormField
            value={ email }
            placeholder='user.email'
            type='email'
            callback={ handleChange('email') }
            hasError={ props.hasError('email')}  />
          { pwmsg }
          <FormField
            value={ password }
            placeholder='user.password'
            type='password'
            callback={ handleChange('password') }
            hasError={ props.hasError('password')}  />
        </div>
      </Card>
    </div>
  );
};

export default Info;
