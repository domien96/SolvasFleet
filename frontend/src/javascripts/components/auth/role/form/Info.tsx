import React from 'react';

import FormField from '../../../forms/FormField.tsx';
import Checkbox from './Checkbox.tsx';
import Card from '../../../app/Card.tsx';

interface Props {
  handleChange: (field : string, e : any) => void;
  hasError: (e : any) => boolean;
  role : RoleData;
  permissions : string[];
}

const Info : React.StatelessComponent<Props> = props => {
  let handleChange = (field : Role.Field) => {
    return (e : any) => {
      props.handleChange(field, e);
    }
  }

  var { name, permissions } = props.role;

  let checkboxes;
  if(permissions){
    checkboxes = props.permissions.map((permission : string) => {
      let active = "";
      if(permission in permissions){
        active = "active";
      }
      return(<Checkbox label={ permission } active={ active }/>);
    });
  }
    
  return (
    <div className='col-xs-12 col-md-7'>
      <Card>
        <div className='card-title'>
          <h5>Role form</h5>
        </div>
        <div className='card-content'>
          <FormField value={ name }  placeholder='role.name'  type='text'  callback={ handleChange('name') }  hasError={ props.hasError('name')} />
          { checkboxes }
        </div>
      </Card>
    </div>
  );
}

export default Info;
