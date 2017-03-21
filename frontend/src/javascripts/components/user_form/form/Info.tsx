import React from 'react';

import Card from '../../app/Card.tsx';
import FormField from '../../forms/FormField.tsx';

interface Props {
  handleChange : (field : string, e : any) => void;
  hasError : (s : string) => boolean;
  user : MUser;
}

interface FieldProps extends Props {
  field : User.Field;
  type : string;
}

const UFormField : React.StatelessComponent<FieldProps>  = props => {
  const handleChange = (field : User.Field) => {
    return (e : any) => props.handleChange(field, e);
  }

  var { field, type } = props;

  return (
    <FormField
      value={ props.user[field] }
      placeholder={ `user.${field}` }
      type={ type }
      callback={ handleChange(field) }
      hasError={ props.hasError(field)} />
  )
}

const Info : React.StatelessComponent<Props> = (props) => {
  return (
    <div className='col-xs-12 col-md-7'>
      <Card>
        <div className='card-title'>
          <h5>General info</h5>
        </div>
        <div className='card-content'>
          <UFormField field='firstName' type='text' { ...props } />
          <UFormField field='lastName' type='text' { ...props } />
          <UFormField field='email' type='email' { ...props } />
          <UFormField field='password' type='password' { ...props } />
        </div>
      </Card>
    </div>
  );
}

export default Info;
