import React from 'react';

import FormField from '../../forms/FormField.tsx';
import DateForm from '../../forms/DateForm.tsx';
import Card from '../../app/Card.tsx';

interface Props {
  handleChange: (field : string, e : any) => void;
  hasError: (e : any) => boolean;
  contract : ContractData;
}

const Info : React.StatelessComponent<Props> = props => {
  let handleChange = (field : Contract.Field) => {
    return (e : any) => {
      props.handleChange(field, e);
    }
  }

  var { franchise, insuranceCompany, premium, type, vehicle, startDate, endDate } = props.contract;

  return (
    <div className='col-xs-12 col-md-7'>
      <Card>
        <div className='card-title'>
          <h5>General info</h5>
        </div>
        <div className='card-content'>
          <FormField value={ franchise }         placeholder='contract.franchise'                type='number' callback={ handleChange('franchise') }         hasError={ props.hasError('franchise')}         />
          <FormField value={ insuranceCompany }    placeholder='contract.insuranceCompany'           type='number' callback={ handleChange('insuranceCompany') }    hasError={ props.hasError('insuranceCompany')}   />
          <FormField value={ premium }  placeholder='contract.premium'         type='number'  callback={ handleChange('premium') }  hasError={ props.hasError('premium')} />
          <FormField value={ type }       placeholder='contract.type'      type='text' callback={ handleChange('type') }       hasError={ props.hasError('type')}      />
          <FormField value={ vehicle }  placeholder='contract.vehicle' type='number' callback={ handleChange('vehicle') }  hasError={ props.hasError('vehicle')}      />
          <DateForm value={ startDate }  label='contract.startDate' callback={ handleChange('startDate') }  hasError={ props.hasError('startDate')}      />
          <DateForm value={ endDate }  label='contract.endDate' callback={ handleChange('endDate') }  hasError={ props.hasError('endDate')}      />
        </div>
      </Card>
    </div>
  );
}

export default Info;
