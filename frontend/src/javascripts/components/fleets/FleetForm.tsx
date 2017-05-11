import React from 'react';
import FormChoice from '../forms/FormChoice.tsx'
import { th } from '../../utils/utils.ts';
import T from 'i18n-react';

interface Props {
  handleChange : (field: Fleet.Field, e : any) => void;
  onSubmit : (e : any) => void;
  fleet : FleetData;
  icon : string;
}

const FleetForm : React.StatelessComponent<Props> = props => {
  let handleChange = (field : Fleet.Field) => {
    return (e : any) => {
      props.handleChange(field, e);
    }
  }

  let className = ''

  if(props.className !== undefined){
      className = props.className
  }


  return (
    <form method='post' onSubmit={ props.onSubmit } className={'dark-form fleet-form '+className}>
      <h3><label htmlFor='name'>{ T.translate('fleet.name')+':' }</label></h3>
      <span>
        <input type='text' id='name' onChange={ handleChange('name') } value={(props.fleet.name||'').toString()}/>
      </span>

      <div className='actions pull-right'>

        <h3>
          <span className={'glyphicon '+props.icon} onClick={ props.onSubmit }/>
        </h3>
      </div>
      <div className='form-actions'>

      <FormChoice placeholder="Facturation Period" choices={[th(3,'fleet.options.perTrimester'),th(6,'fleet.options.perSemester'),th(12, 'fleet.options.yearly')]}
          value={(props.fleet.facturationPeriod||'').toString()} callback={handleChange('facturationPeriod')}/>
      <FormChoice placeholder="Payment Period" choices={[th(1, 'fleet.options.monthly'),th(3,'fleet.options.perTrimester'),th(6,'fleet.options.perSemester'),th(12, 'fleet.options.yearly')]}
         value={(props.fleet.paymentPeriod||'').toString()} callback={handleChange('paymentPeriod')} />
      </div>

    </form>
  )
}

export default FleetForm;
