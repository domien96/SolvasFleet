import React from 'react';

import FormField from '../../forms/FormField.tsx';
import { Props } from './Info.tsx';

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

export default UFormField;
