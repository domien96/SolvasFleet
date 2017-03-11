import React from 'react';
import { browserHistory } from'react-router';

import fetchUser  from '../../actions/fetch_user.ts';
import deleteUser from '../../actions/delete_user.ts';

class User extends React.Component<User.Props, User.State> {

  constructor() {
    super();
    this.state = { user : {} };
    this.deleteUser = this.deleteUser.bind(this);
  }

  fetchUser(id : number) {
    fetchUser(id)
      .then((data : any) => {
        this.setState({ user: data })
      });
  }

  componentDidMount() {
    this.fetchUser(this.props.params.id);
  }

  componentWillReceiveProps(nextProps : User.Props) {
    if (nextProps.params.id != this.props.params.id) {
      this.fetchUser(nextProps.params.id);
    }
  }

  deleteUser(){
    var reloadUsers = this.props.fetchUsers;
    deleteUser(this.props.params.id)
    .then(function(this: any) {
      reloadUsers();
    });
    browserHistory.push('/users');
  }

  render() {
    var { first_name, last_name, email, password } = this.state.user;

    return (
      <div className='card-content user'>
        <h2>{ first_name } { last_name }</h2>
        <div className='row actions'>
          <div className='col-sm-6'>
            <button className='btn btn-default form-control'>
              <span className='glyphicon glyphicon-edit' />
              Edit
            </button>
          </div>
          <div className='col-sm-6'>
            <button onClick = { this.deleteUser } className='btn btn-danger form-control'>
              <span className='glyphicon glyphicon-remove' />
              Delete
            </button>
          </div>
        </div>
        <h5>Information</h5>
        <div>email: { email }</div>
        <div>password: { password }</div>
      </div>
    );
  }
}

export default User;
