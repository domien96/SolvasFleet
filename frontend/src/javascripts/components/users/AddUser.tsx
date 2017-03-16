import React from 'react';
import { browserHistory } from 'react-router';

import Header     from '../app/Header.tsx';
import UserForm   from './UserForm.tsx';

import createUser   from '../../actions/create_user.ts';
import { hasError } from '../../utils/utils.ts';

class AddUser extends React.Component<{}, User.UForm.State> {

  constructor() {
    super();
    this.state = {
      errors: [],
      user: {}
    };
    this.handleChange = this.handleChange.bind(this);
    this.onSubmit     = this.onSubmit.bind(this);
  }

  public handleChange(field : User.Field, e : any) : any {
    var user : User = this.state.user;
    user[field] = e.target.value;
    this.setState({ user });
  }

  public onSubmit(e : any) : void {
    e.preventDefault();
    let setErrors = (e : Form.Error[]) => this.setState({ errors: e });

    createUser(this.state.user)
    .then(function(response) {
      return response.json().then(function(data) {
        if (response.ok) {
          browserHistory.push('/users/' + data.id);
        } else {
          setErrors(data.errors.map(function(e : any) {
            return { field: e, error: 'null' };
          }));
        }
      });
    });
  }

  render() {
    return (
      <div>
        <Header>
          <h2>Add A New User</h2>
        </Header>
        <UserForm
          user={ this.state.user }
          onSubmit={ this.onSubmit }
          handleChange={ this.handleChange }
          hasError={ hasError.bind(this) }
          errors={ this.state.errors }
          />
      </div>
    );
  }
}

export default AddUser;
