import React  from 'react';

interface Props {
  authorized: boolean;
}

const DynamicGuiComponent: React.StatelessComponent<Props> = props => {
  if (!props.authorized) {
    return null;
  }

  if (React.Children.count(props.children) === 1) {
    return React.Children.only(props.children);
  } else {
    return (
      <div>
        { props.children }
      </div>
    );
  }
};

export default DynamicGuiComponent;
