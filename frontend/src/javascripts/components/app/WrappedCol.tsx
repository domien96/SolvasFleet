import React from 'react';

class WrappedCol extends React.Component<WrappedColProps, {}> {
  render() {
    const colClass = 'col-lg-' + (this.props.cols || 12);

    return (
      <div className='wrapper'>
        <div className='row'>
          <div className={ colClass }>
            { this.props.children }
          </div>
        </div>
      </div>
    );
  }
}

export default WrappedCol;
