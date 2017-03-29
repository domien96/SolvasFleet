import React from 'react';
import classNames from 'classnames';

interface Props {
  className?: string;
}

const Card : React.StatelessComponent<Props> = props => {
  const classes = classNames(
    'card',
    props.className
  );

  return (
    <div className={ classes }>
      { props.children }
    </div>
  );
}

export default Card;
