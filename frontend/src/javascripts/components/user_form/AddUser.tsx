import React from 'react';

import Header from '../app/Header.tsx';
import UserForm from './UserForm.tsx';

import { postUser } from '../../actions/user_actions.ts';
import { hasError } from '../../utils/utils.ts';
import { redirect_to } from'../../routes/router.tsx';
import Errors from '../../modules/Errors.ts';

interface State {
  errors: Form.Error[];
  user: UserData;
}

class AddUser extends React.Component<{}, State> {

  constructor() {
    super();
    this.state = {
      errors: [],
      user: {},
    };
    this.handleChange = this.handleChange.bind(this);
    this.onSubmit     = this.onSubmit.bind(this);
  }

  public handleChange(field: User.Field, e: any): any {
    const user: UserData = this.state.user;
    user[field] = e.target.value;
    this.setState({ user });
  }

  public onSubmit(e: any): void {
    e.preventDefault();
    let success = (data : any) => redirect_to(`/users/${data.id}`);
    let setErrors = (e : Form.Error[]) => this.setState({ errors: e });
    const setErrors = (es: Form.Error[]) => this.setState({ errors: es });
    const success = (data: any) => redirect_to(`/users/${data.id}`);

    postUser(this.state.user, success, Errors.handle(setErrors));
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
