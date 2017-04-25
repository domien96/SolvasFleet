import React from 'react';

interface Props {
  label : string;
  active: boolean;
  onChange: (e : any) => void;
}

const Checkbox : React.StatelessComponent<Props> = props => {
  return (
    <div className='checkbox'>
      <label>
        <input type="checkbox" value={ props.label } onChange={ props.onChange } checked={ props.active } /> { props.label }
      </label>
    </div>
  )
}

export default Checkbox;
