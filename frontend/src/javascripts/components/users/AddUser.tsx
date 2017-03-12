import React from 'react';
import { browserHistory } from 'react-router';

import Header     from '../app/Header.tsx';
import UserForm   from './UserForm.tsx';

import createUser from '../../actions/create_user.ts';

class AddUser extends React.Component<User.Props, User.New.State> {

  constructor() {
    super();
    this.state = {
      errors: [ { field: 'first_name', error: 'null' }],
      user: {}
    };
    this.handleChange = this.handleChange.bind(this);
    this.onSubmit     = this.onSubmit.bind(this);
    this.hasError     = this.hasError.bind(this);
  }

  public handleChange(field : User.Field, e : any) : any {
    var newUser : User = this.state.user;
    newUser[field] = e.target.value;
    this.setState({ user: newUser });
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

  public hasError(k : string) : boolean {
    const errors = this.state.errors.filter(function(el) {return el.field == k; });
    return (errors.length != 0);
  }

  render() {
    return (
      <div>
        <Header>
          <h2>Add A New User</h2>
        </Header>
        <UserForm user={this.state.user}onSubmit={ this.onSubmit } handleChange={ this.handleChange } hasError={ this.hasError } errors={ this.state.errors }/>
      </div>
    );
  }
}

export default AddUser;
