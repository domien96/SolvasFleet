import React from 'react';

import FormField from '../../../forms/FormField.tsx';
import Checkbox from './Checkbox.tsx';
import Card from '../../../app/Card.tsx';

interface Props {
  handleChange: (e: any) => void;
  hasError: (e: any) => boolean;
  role: RoleData;
  permissions: string[];
}

const Info: React.StatelessComponent<Props> = props => {
  const { name, permissions } = props.role;

  let checkboxes;
  if (permissions) {
    checkboxes = props.permissions.map((permission: string, i: number) => {
      let active: boolean = false;
      if (permissions.includes(permission)) {
        active = true;
      }
      return(<Checkbox key={ i } label={ permission } active={ active } onChange={ props.handleChange }/>);
    });
  }

  return (
    <div className='col-xs-12 col-md-7'>
      <Card>
        <div className='card-content'>
          <FormField
            value={ name }
            placeholder='role.name'
            type='text'
            callback={ props.handleChange }
            hasError={ props.hasError('name')} />
          { checkboxes }
        </div>
      </Card>
    </div>
  );
};

export default Info;
