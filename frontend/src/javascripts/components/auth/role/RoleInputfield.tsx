import React from 'react';
import T from 'i18n-react';
import { Typeahead } from 'react-bootstrap-typeahead';
import { fetchRoles } from '../../../actions/auth_actions.ts';
import classNames from 'classnames';

interface Props {
  value: number[];
  callback: (e: any) => void;
  placeholder: string;
  hasError: boolean;
}

interface State {
  roles: RoleData[];
}

class RoleInputfield extends React.Component<Props, State> {
  constructor(props: any) {
    super(props);
    this.state = { roles: [] };
    this.fetchRoles = this.fetchRoles.bind(this);
    this.handleChange = this.handleChange.bind(this);
  }

  componentDidMount() {
    this.fetchRoles();
  }

  componentWillReceiveProps(nextProps: any) {
    if (nextProps.value !== this.props.value) {
      this.fetchRoles();
    }
  }

  handleChange(selectedRoles: string[]) {
    if (selectedRoles) {
      this.props.callback({ target: { value: parseInt(selectedRoles[0].split(':')[0], 10) } });
    }
  }

  fetchRoles() {
    fetchRoles((data: any) => {
      this.setState({ roles: data.data })
    });
  }

  render() {
    let optionList: string[] = [];
    let selected: string[] = [];
    if (this.state.roles) {
      optionList = this.state.roles.map((c: RoleData) => {
        let option = `${c.id.toString()}: ${c.name}`;
        if (c.id === this.props.value[0]) {
          selected.push(option);
        }
        return option;
      });
    }

    const label = T.translate(this.props.placeholder);
    const wrapperClasses = classNames('form-group', { 'has-error': this.props.hasError });

    return(
      <div className={ wrapperClasses }>
        <label>{ label }</label>
        <Typeahead onChange={ this.handleChange } options={ optionList } selected={ selected }/>
      </div>
    );
  }
}

export default RoleInputfield;
