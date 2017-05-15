import React from 'react';

namespace NestedCheckbox {
  export interface Props {
    values: Node[];
    cb: any;
  }
  export interface Node {
    group: string;
    id: number;
    checked?: boolean;
  }
  export interface State {
    values: Node[];
  }
}

class NestedCheckbox extends React.Component<NestedCheckbox.Props, NestedCheckbox.State> {
  static childContextTypes = {
    childHandleChange: React.PropTypes.func,
    childIsChecked:    React.PropTypes.func,
    handleChange:      React.PropTypes.func,
    isChecked:         React.PropTypes.func,
    isIndeterminate:   React.PropTypes.func,
  };

  constructor(props: NestedCheckbox.Props) {
    super(props);
    this.filterGroup = this.filterGroup.bind(this);
    this.state = { values: [] };
  }

  componentWillReceiveProps({ values }: NestedCheckbox.Props) {
    if(this.props.values.length==0||this.props.values!=values){
    const s = values.map((p) => ({ ...p, checked: false }));
    this.setState({ values: s });}
  }

  filterGroup(group: string): boolean[] {
    return this.state.values
      .filter((n) => (n.group === group))
      .map((n) => n.checked);
  }

  childIsChecked(id: number): boolean {
    return this.state.values.find((n) => {
      return n.id === id;
    }).checked;
  }

  childHandleChange(id: number): void {
    const values = this.state.values.map((n) => {
      if (n.id === id) {
        n.checked = !n.checked;
      }
      return n;
    });
    this.setState({ values });
    this.props.cb(values);
  }

  isChecked(group: string): boolean {
    return this.filterGroup(group).every((n) => n);
  }

  isIndeterminate(group: string): boolean {
    const b = this.filterGroup(group);
    return !(b.every((n) => !n) || b.every((n) => n));
  }

  handleChange(group: string): void {
    const old = this.isChecked(group);
    const values = this.state.values.map((n) => {
      if (n.group === group) {
        n.checked = !old;
      }
      return n;
    });
    this.setState({ values });
    this.props.cb(values);
    }

  getChildContext() {
    return {
      childHandleChange: this.childHandleChange.bind(this),
      childIsChecked:    this.childIsChecked.bind(this),
      handleChange:      this.handleChange.bind(this),
      isChecked:         this.isChecked.bind(this),
      isIndeterminate:   this.isIndeterminate.bind(this),
    };
  }

  render() {
    return (
      <div>
        { this.props.children }
      </div>
    );
  }
}

export default NestedCheckbox;
