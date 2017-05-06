import React from 'react';

import FormField from '../../forms/FormField.tsx';
import FormChoice from '../../forms/FormChoice.tsx';
import DateForm from '../../forms/DateForm.tsx';
import Card from '../../app/Card.tsx';
import CompanyInputfield from '../../client/CompanyInputfield.tsx'


interface Props {
  handleChange: (field : string, e : any, type : string) => void;
  hasError: (e : any) => boolean;
  contract : ContractData;
  types: string[];
}

const Info : React.StatelessComponent<Props> = props => {
  let handleChange = (field : Contract.Field, type : string) => {
    return (e : any) => {
      props.handleChange(field, e, type);
    }
  }

  var { franchise, insuranceCompany, premium, type, vehicle, startDate, endDate } = props.contract;

  var choices : Table.Head.Data[] = props.types.map((t: string) => {
    return { key: t, label: 'contract.types.'+t }
  });

  return (
    <div className='col-xs-12 col-md-7'>
      <Card>
        <div className='card-content'>
          <FormField value={ franchise }           placeholder='contract.franchise'                type='number' callback={ handleChange('franchise', 'text') }         hasError={ props.hasError('franchise')}         />
          <CompanyInputfield value={ [insuranceCompany] } placeholder='contract.insuranceCompany'  query={ {type: 'InsuranceCompany'} }   callback={ handleChange('insuranceCompany', 'text') }  hasError={ props.hasError('insuranceCompany')} />
          <FormField value={ premium }             placeholder='contract.premium'         type='number'  callback={ handleChange('premium', 'text') }  hasError={ props.hasError('premium')} />
          <FormChoice value={ type }               placeholder='contract.type'       choices={ choices } callback={ handleChange('type', 'text') } />
          <FormField value={ vehicle }             placeholder='contract.vehicle' type='number' callback={ handleChange('vehicle', 'text') }  hasError={ props.hasError('vehicle')}      />
          <DateForm value={ startDate }   label='contract.startDate' callback={ handleChange('startDate', 'date') }  hasError={ props.hasError('startDate')}      />
          <DateForm value={ endDate }     label='contract.endDate' callback={ handleChange('endDate', 'date') }  hasError={ props.hasError('endDate')}      />
        </div>
      </Card>
    </div>
  );
}

export default Info;
