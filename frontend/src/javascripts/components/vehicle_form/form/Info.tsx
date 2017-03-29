import React from 'react';

import FormField from '../../forms/FormField.tsx';
import FormChoice from '../../forms/FormChoice.tsx';
import { th } from '../../../utils/utils.ts';

import Card from '../../app/Card.tsx';

interface Props {
  handleChange: (field : string, e : any) => void;
  hasError: (e : any) => boolean;
  vehicle : Vehicle;
}

const Info : React.StatelessComponent<Props> = props => {
  let handleChange = (field : Vehicle.Field) => {
    return (e : any) => {
      props.handleChange(field, e);
    }
  }

  var { licensePlate, vin, fleet, leasingCompany, brand, model, type, mileage, year, value } = props.vehicle;
  
  const choices = [
    th('personalCar', 'vehicle.options.personalCar'),
    th('van', 'vehicle.options.van'),
    th('semiTrailer', 'vehicle.options.semiTrailer'),
    th('trailer', 'vehicle.options.trailer'),
    th('truck', 'vehicle.options.truck')
  ];
  return (
    <div className='col-xs-12 col-md-7'>
      <Card>
        <div className='card-title'>
          <h5>General info</h5>
        </div>
        <div className='card-content'>
          <FormField value={ licensePlate }    placeholder='vehicle.licensePlate'     type='text'     callback={ handleChange('licensePlate') }    hasError={ props.hasError('licensePlate')} />
          <FormField value={ vin }             placeholder='vehicle.vin'              type='text'     callback={ handleChange('vin') }             hasError={ props.hasError('vin')}      />
          <FormField value={ fleet }           placeholder='vehicle.fleet'            type='text'     callback={ handleChange('fleet') }           hasError={ props.hasError('fleet')}   />
          <FormField value={ leasingCompany }  placeholder='vehicle.leasingCompany'   type='text'     callback={ handleChange('leasingCompany') }  hasError={ props.hasError('leasingCompany')}  />
          <FormField value={ brand }           placeholder='vehicle.brand'            type='text'     callback={ handleChange('brand') }           hasError={ props.hasError('brand')}      />
          <FormField value={ model }           placeholder='vehicle.model'            type='text'     callback={ handleChange('model') }           hasError={ props.hasError('model')}   />
          <FormChoice value={ type }           placeholder='vehicle.type'         choices={ choices } callback={ handleChange('type') } />
          <FormField value={ mileage }         placeholder='vehicle.mileage'          type='number'   callback={ handleChange('mileage') }         hasError={ props.hasError('mileage')}  />
          <FormField value={ year }            placeholder='vehicle.year'             type='number'   callback={ handleChange('year') }            hasError={ props.hasError('year')}      />
          <FormField value={ value }           placeholder='vehicle.value'            type='number'   callback={ handleChange('value') }           hasError={ props.hasError('value')}   />
        </div>
      </Card>
    </div>
  );
}

export default Info;