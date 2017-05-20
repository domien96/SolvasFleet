import React  from 'react';
import _ from 'lodash';
import { parseClaims } from '../../modules/Auth.ts';

interface Props {
  authorized: boolean;
}

class DynamicGuiComponent extends React.Component<Props,{}> {

  render() {
    const comp = (<div>{ this.props.children }</div>);

    return this.props.authorized ? comp : null;
  }
}

export default DynamicGuiComponent;
