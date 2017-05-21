import React  from 'react';

interface Props {
  authorized: boolean;
}

const DynamicGuiComponent: React.StatelessComponent<Props> = props => {
  if (!props.authorized) {
    return null;
  }

  return React.Children.only(props.children);
}

export default DynamicGuiComponent;
