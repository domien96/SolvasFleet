import React from 'react';
import { browserHistory } from'react-router';

import UserCard from './UserCard.tsx';
import fetchUser from '../../actions/fetch_user.ts';
import deleteUser from '../../actions/delete_user.ts';

interface Props {
  params : { id : number };
  fetchUsers : () => void;
}

interface State {
  user : MUser;
}

class User extends React.Component<Props, State> {

  constructor() {
    super();
    this.state = { user: {} };
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
    .then(() => reloadUsers());
    browserHistory.push('/users');
  }

  render() {
    return (
      <UserCard user={ this.state.user } handleDelete={ this.deleteUser } />
    );
  }
}

export default User;
