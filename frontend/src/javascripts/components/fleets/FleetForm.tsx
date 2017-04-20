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

  return (
    <form method='post' onSubmit={ props.onSubmit } className='fleet-form'>
      <h3><label htmlFor='name'>Name:</label></h3>
      <span>
        <input type='text' id='name' onChange={ handleChange('name') }/>
        <FormChoice placeholder="Facturation Period" choices={[th(91,'fleet.options.perTrimester'),th(186,'fleet.options.perSemester'),th(365, 'fleet.options.yearly')]}
            value={(props.fleet.facturationPeriod||'').toString()} callback={handleChange('facturationPeriod')}/>
        <FormChoice placeholder="Payment Period" choices={[th(31, 'fleet.options.monthly'),th(91,'fleet.options.perTrimester'),th(186,'fleet.options.perSemester'),th('365', 'fleet.options.yearly')]}
           value={(props.fleet.paymentPeriod||'').toString()} callback={handleChange('paymentPeriod')} />
      </span>



      <div className='actions pull-right'>
        <h3>
          <span className='glyphicon glyphicon-plus' onClick={ props.onSubmit }/>
        </h3>
      </div>
    </form>
  )
}

export default FleetForm;
