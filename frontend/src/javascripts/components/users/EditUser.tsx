import React from 'react';
import { USERS_URL } from '../../constants/constants.ts';
import { browserHistory } from 'react-router';

import Header from '../app/Header.tsx';
import UserForm from './UserForm.tsx';

import fetchUser from '../../actions/fetch_user.ts';

class EditUser extends React.Component<{}, any> {
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

  componentDidMount() {
    fetchUser(1)
      .then((data : any) => {
        console.log('fetched');
        console.log(data);
        this.setState({ user: data })
      });
  }

  public hasError(k : string) : boolean {
    const errors = this.state.errors.filter(function(el : Form.Error) {return el.field == k; });
    return (errors.length != 0);
  }

  handleChange(field : User.Field, e : any) : any {
    var newUser : User = this.state.user;
    newUser[field] = e.target.value;
    this.setState({ user: newUser });
  }

  onSubmit(e : any) : void {
    e.preventDefault();
    fetch(USERS_URL + '/' + this.state.user.id, {
      method: 'PUT',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(this.state.user)
    })
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
          <h2>Edit User</h2>
        </Header>
        <UserForm user={ this.state.user } onSubmit={ this.onSubmit } handleChange={ this.handleChange } hasError={ this.hasError } errors={ this.state.errors }/>
      </div>
    )
  }
}

export default EditUser;
