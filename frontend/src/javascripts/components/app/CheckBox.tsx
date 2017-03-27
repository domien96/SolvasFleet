import React from 'react';
import ReactDOM from 'react-dom';

class Checkbox extends React.Component<any,{}> {
  componentDidMount() {
    if (this.props.indeterminate === true) {
      this._setIndeterminate(true);
    }
  }

  componentDidUpdate(previousProps : any) {
    if (previousProps.indeterminate !== this.props.indeterminate) {
      this._setIndeterminate(this.props.indeterminate);
    }
  }

  _setIndeterminate(indeterminate : boolean) {
    const node = ReactDOM.findDOMNode<HTMLInputElement>(this);
    node.indeterminate = indeterminate;
  }

  render() {
    const { indeterminate, type, ...props } = this.props;
    return <input type="checkbox" {...props} />;
  }
}

export default Checkbox;
