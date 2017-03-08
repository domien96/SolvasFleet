import React from 'react';

class Header extends React.Component<CardProps, {}> {
  render() {
    return (
      <div className='header'>
        { this.props.children }
      </div>
    );
  }
}

export default Header;
