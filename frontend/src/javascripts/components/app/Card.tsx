import React from 'react';
import classNames from 'classnames';

class Card extends React.Component<CardProps, {}> {
  render() {
    const classes = classNames(
      'card',
      this.props.className
    );

    return (
      <div className={ classes }>
        { this.props.children }
      </div>
    );
  }
}

export default Card;
