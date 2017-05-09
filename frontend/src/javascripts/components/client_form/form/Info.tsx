import React from 'react';

import FormField from '../../forms/FormField.tsx';
import FormChoice from '../../forms/FormChoice.tsx';
import Card from '../../app/Card.tsx';
import { th } from '../../../utils/utils.ts';


interface Props {
  handleChange: (field : string, isAddress : boolean, e : any) => void;
  hasError: (e : any) => boolean;
  company : CompanyData;
}

const Info : React.StatelessComponent<Props> = props => {
  let handleChange = (field : Company.Field, isAddress : boolean) => {
    return (e : any) => {
      props.handleChange(field, isAddress, e);
    }
  }

  var { name, vatNumber, phoneNumber, address, type } = props.company;
  var { street, houseNumber, city, postalCode, country } = address;

  const choices = [
    th('Customer', 'company.type.Customer'),
    th('LeasingCompany', 'company.type.LeasingCompany'),
    th('InsuranceCompany', 'company.type.InsuranceCompany')
  ];

  return (
    <div className='col-xs-12 col-md-7'>
      <Card>
        <div className='card-content'>
          <FormField value={ name }         placeholder='company.name'                type='text' callback={ handleChange('name', false) }         hasError={ props.hasError('name')}         />
          <FormField value={ vatNumber }    placeholder='company.vatNumber'           type='text' callback={ handleChange('vatNumber', false) }    hasError={ props.hasError('vatNumber')}   />
          <FormChoice value={ type }        placeholder='company.types'               choices={ choices } callback={ handleChange('type', false) } />
          <FormField value={ phoneNumber }  placeholder='company.phoneNumber'         type='tel'  callback={ handleChange('phoneNumber', false) }  hasError={ props.hasError('phoneNumber')} />
          <FormField value={ street }       placeholder='company.address.street'      type='text' callback={ handleChange('street', true) }       hasError={ props.hasError('street')}      />
          <FormField value={ houseNumber }  placeholder='company.address.houseNumber' type='text' callback={ handleChange('houseNumber', true) }  hasError={ props.hasError('houseNumber')}      />
          <FormField value={ city }         placeholder='company.address.city'        type='text' callback={ handleChange('city', true) }         hasError={ props.hasError('city')}      />
          <FormField value={ postalCode }   placeholder='company.address.postalCode'  type='text' callback={ handleChange('postalCode', true) }   hasError={ props.hasError('postalCode')}      />
          <FormField value={ country }      placeholder='company.address.country'     type='text' callback={ handleChange('country', true) }      hasError={ props.hasError('country')}      />
        </div>
      </Card>
    </div>
  );
}

export default Info;
