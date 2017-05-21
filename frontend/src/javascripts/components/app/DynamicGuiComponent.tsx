import React  from 'react';

interface Props {
  authorized: boolean;
}

const DynamicGuiComponent: React.StatelessComponent<Props> = props => {
  if (!props.authorized) {
    return null;
  }

  return (
    <div>
      props.children;
    </div>
  );
}

export default DynamicGuiComponent;
