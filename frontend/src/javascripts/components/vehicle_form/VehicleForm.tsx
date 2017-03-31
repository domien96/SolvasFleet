import React    from 'react';

import Errors     from '../app/Errors.tsx';
import Actions from '../forms/Actions.tsx';

import Info from './form/Info.tsx';

interface Props {
  onSubmit     : (e : any) => void;
  handleChange : (field : Vehicle.Field, e : any) => void;
  errors       : Form.Error[];
  hasError     : (field : Vehicle.Field) => boolean;
  vehicle      : VehicleData;
}

const VehicleForm : React.StatelessComponent<Props> = props => {
  const submit = props.vehicle.id != null ? 'form.update' : 'form.create';

  return (
    <form method='post' onSubmit={ props.onSubmit } >
      <div className='wrapper'>
        <div className='row'>
          <Errors errors={ props.errors } />
          <Info vehicle={ props.vehicle } handleChange={ props.handleChange } hasError={ props.hasError }/>
          <div className='col-xs-12 col-md-5'>
            <div className='row'>
              <Actions submitLabel={ submit } cancelUrl={ `/vehicles/${props.vehicle.id || ''}` } model='vehicle' />
            </div>
          </div>
        </div>
      </div>
    </form>
  );
}

export default VehicleForm;
