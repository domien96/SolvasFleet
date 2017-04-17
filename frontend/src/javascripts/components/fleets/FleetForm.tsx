import React from 'react';

interface Props {
  handleChange : (field: Fleet.Field, e : any) => void;
  onSubmit : (e : any) => void;
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
