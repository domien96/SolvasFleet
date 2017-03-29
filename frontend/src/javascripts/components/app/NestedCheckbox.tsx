import React from 'react';

namespace NestedCheckbox {
  export interface Props {
    values : Node[];
  }
  export interface Node {
    group    : string;
    id       : number
    checked? : boolean;
  }
  export interface State {
    values : Node[];
  }
}

class NestedCheckbox extends React.Component<NestedCheckbox.Props, NestedCheckbox.State> {
  static childContextTypes = {
    childIsChecked:    React.PropTypes.func,
    childHandleChange: React.PropTypes.func,
    isChecked:         React.PropTypes.func,
    isIndeterminate:   React.PropTypes.func,
    handleChange:      React.PropTypes.func
  }

  constructor(props : NestedCheckbox.Props) {
    super(props);
    this.filterGroup = this.filterGroup.bind(this);
    this.state = { values: [] };
  }

  componentWillReceiveProps({ values } : NestedCheckbox.Props) {
    const s = values.map(({ ...p }) => { return { ...p, checked: false }});
    this.setState({ values: s });
  }

  filterGroup(group : string) : boolean[] {
    return this.state.values
      .filter((n) => { return n.group == group; })
      .map((n) => { return n.checked; })
  }

  childIsChecked(id : number) : boolean {
    return this.state.values.find((n) => {
      return n.id == id;
    }).checked;
  }

  childHandleChange(id : number) : void {
    const values = this.state.values.map((n) => {
      if (n.id == id) {
        n.checked = !n.checked;
      }
      return n;
    })
    this.setState({ values });
  }

  isChecked(group : string) : boolean {
    return this.filterGroup(group).every((n) => n);
  }

  isIndeterminate(group : string) : boolean {
    let b = this.filterGroup(group);
    return !(b.every((n) => !n) || b.every((n) => n));
  }

  handleChange(group : string) : void {
    const old = this.isChecked(group);
    const values = this.state.values.map((n) => {
      if (n.group == group) {
        n.checked = !old;
      }
      return n;
    });
    this.setState({ values });
  }

  getChildContext() {
    return {
      childIsChecked:    this.childIsChecked.bind(this),
      childHandleChange: this.childHandleChange.bind(this),
      isChecked:         this.isChecked.bind(this),
      isIndeterminate:   this.isIndeterminate.bind(this),
      handleChange:      this.handleChange.bind(this)
    }
  }

  render () {
    return (
      <div>
        { this.props.children }
      </div>
    );
  }
}

export default NestedCheckbox;
