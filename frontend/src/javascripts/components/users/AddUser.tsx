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
      errors: [ { field: 'firstName', error: 'null' }],
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

    createUser(this.state.user)
    .then(function(response) {
      return response.json()
    })
    .then(() => {
      browserHistory.push('/users');
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
