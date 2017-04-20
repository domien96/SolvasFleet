import React from 'react';
import FormChoice from '../forms/FormChoice.tsx'
import { th } from '../../utils/utils.ts';

interface Props {
  handleChange : (field: Fleet.Field, e : any) => void;
  onSubmit : (e : any) => void;
  fleet : FleetData;
}

const FleetForm : React.StatelessComponent<Props> = props => {
  let handleChange = (field : Fleet.Field) => {
    return (e : any) => {
      props.handleChange(field, e);
    }
  }
  const choices = [
    th(31, 'fleet.options.monthly'),
    th(365, 'fleet.options.yearly')
  ];

  return (
    <form method='post' onSubmit={ props.onSubmit } className='fleet-form'>
      <h3><label htmlFor='name'>Name:</label></h3>
      <span>
        <input type='text' id='name' onChange={ handleChange('name') }/>
        <FormChoice placeholder="Facturation Period" choices={choices} value={props.fleet.facturationPeriod} callback={handleChange('facturationPeriod')}/>
        <FormChoice placeholder="Payment Period" choices={choices} value={props.fleet.paymentPeriod} callback={handleChange('paymentPeriod')} />
      </span>



      <div className='actions pull-right'>
        <h3>
          <span className='glyphicon glyphicon-plus' />
        </h3>
      </div>
    </form>
  )
}

export default FleetForm;
