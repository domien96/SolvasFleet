import React from 'react';
import { browserHistory } from 'react-router';

import Header from '../app/Header.tsx';
import UserForm from './UserForm.tsx';

import { fetchUser, putUser } from '../../actions/user_actions.ts';
import { hasError } from '../../utils/utils.ts';

class EditUser extends React.Component<User.Props, User.UForm.State> {
  constructor() {
    super();
    this.state = {
      errors: [],
      user: {}
    };
    this.handleChange = this.handleChange.bind(this);
    this.onSubmit     = this.onSubmit.bind(this);
  }

  componentDidMount() {
    fetchUser(this.props.params.id, (data : any) => this.setState({ user: data }));
  }

  handleChange(field : User.Field, e : any) : any {
    var user : User = this.state.user;
    user[field] = e.target.value;
    this.setState({ user });
  }

  onSubmit(e : any) : void {
    e.preventDefault();
    let setErrors = (e : Form.Error[]) => this.setState({ errors: e });
    let success = () => browserHistory.push('/users/' + this.state.user.id);
    let fail = (data : any) => {
      setErrors(data.errors.map(function(e : any) {
        return { field: e, error: 'null' };
      }));
    }

    putUser(this.state.user.id, this.state.user, success, fail);
  }

  render() {
    return (
      <div>
        <Header>
          <h2>Edit User</h2>
        </Header>
        <UserForm
          user={ this.state.user }
          onSubmit={ this.onSubmit }
          handleChange={ this.handleChange }
          hasError={ hasError.bind(this) }
          errors={ this.state.errors }
          />
      </div>
    )
  }
}

export default EditUser;