import React from 'react';

interface Props {
  label : string;
  active: string;
  onChange: (e : any) => void;
}

const Checkbox : React.StatelessComponent<Props> = props => {
  return (
    <div className='checkbox'>
      <label className={ " "+props.active }>
        <input type="checkbox" value={ props.label } onChange={ props.onChange } /> { props.label }
      </label>
    </div>
  )
}

export default Checkbox;
