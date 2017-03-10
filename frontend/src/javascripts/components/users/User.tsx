import React from 'react';
import { browserHistory } from'react-router';

import fetchUser from '../../actions/fetch_user.ts';
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

  public deleteUser(){
    deleteUser(this.props.params.id);
    browserHistory.push('/users');
  }

  render() {
    var { first_name, last_name, email, password } = this.state.user;

    return (
      <div className='card-content user'>
        <span className='pull-right'>
          <button className='btn btn-default'>
            <span className='glyphicon glyphicon-edit' />
          </button>
          <button onClick = { this.deleteUser } className='btn btn-default'>
            <span className='glyphicon glyphicon-remove' />
          </button>
        </span>
        <h2>{ first_name } { last_name }</h2>
        <h5>Information</h5>
        <div>email: { email }</div>
        <div>password: { password }</div>
      </div>
    );
  }
}

export default User;
