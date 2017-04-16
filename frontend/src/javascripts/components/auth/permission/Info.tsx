import React from 'react';

import FormField from '../../forms/FormField.tsx';
import Card from '../../app/Card.tsx';

interface Props {
  handleChange: (field : string, isAddress : boolean, e : any) => void;
  hasError: (e : any) => boolean;
  permission : PermissionData;
}

const Info : React.StatelessComponent<Props> = props => {
  let handleChange = (field : Permission.Field, isAddress : boolean) => {
    return (e : any) => {
      props.handleChange(field, e);
    }
  }

  var { action, resource } = props.permission;
  

  return (
    <div className='col-xs-12 col-md-7'>
      <Card>
        <div className='card-title'>
          <h5>General info</h5>
        </div>
        <div className='card-content'>
          <FormField value={ action }     placeholder='permission.action'    type='text' callback={ handleChange('action') }    hasError={ props.hasError('action')}   />
          <FormField value={ resource }   placeholder='permission.resource'  type='text' callback={ handleChange('resource') }  hasError={ props.hasError('resource')} />
        </div>
      </Card>
    </div>
  );
}

export default Info;
