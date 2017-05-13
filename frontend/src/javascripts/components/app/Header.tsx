import React from 'react';

const Header: React.StatelessComponent<{}> = props => {
  return (
    <div className='header'>
      { props.children }
    </div>
  );
};

export default Header;
