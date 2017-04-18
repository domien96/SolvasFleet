import React from 'react';

interface Props {
  label : string;
  active: string;
}

const Checkbox : React.StatelessComponent<Props> = props => {
  return (
    <div className='checkbox'>
      <label className={ "btn btn-default "+props.active }>
        <input type="checkbox" value={ props.label } /> { props.label }
      </label>
    </div>
  )
}

export default Checkbox;
