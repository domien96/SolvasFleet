import React from 'react';
import { USERS_URL } from '../../constants/constants.ts';
import { browserHistory } from 'react-router';

import Header from '../app/Header.tsx';
import UserForm from './UserForm.tsx';

import fetchUser    from '../../actions/fetch_user.ts';
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
    fetchUser(this.props.params.id)
      .then((data : any) => {
        this.setState({ user: data })
      });
  }

  handleChange(field : User.Field, e : any) : any {
    var user : User = this.state.user;
    user[field] = e.target.value;
    this.setState({ user });
  }

  onSubmit(e : any) : void {
    e.preventDefault();
    let setErrors = (e : Form.Error[]) => this.setState({ errors: e });

    fetch(USERS_URL + '/' + this.state.user.id, {
      method: 'PUT',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(this.state.user)
    })
    .then(function(response) {
      return response.json().then(function(data) {
        if (response.ok) {
          browserHistory.push('/users/' + data.id);
        } else {
          setErrors(data.errors.map(function(e : any) {
            return { field: e.field, error: 'null' };
          }));
        }
      });
    });
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
