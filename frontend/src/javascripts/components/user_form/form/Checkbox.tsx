import React from 'react';

interface Props {
  label: string;
}

const Checkbox: React.StatelessComponent<Props> = props => {
  return (
    <div className='checkbox'>
      <label>
        <input type="checkbox" value='' /> { props.label }
      </label>
    </div>
  );
};

export default Checkbox;
