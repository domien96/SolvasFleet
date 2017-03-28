import React from 'react';

import UserCard from './UserCard.tsx';
import { fetchUser, deleteUser } from '../../actions/user_actions.ts';
import { redirect_to } from'../../router.tsx';

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
    fetchUser(id, ((data) => {
      this.setState({ user: data })
    }));
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
    deleteUser(this.props.params.id, () => reloadUsers());
    redirect_to('/users');
  }

  render() {
    return (
      <UserCard user={ this.state.user } handleDelete={ this.deleteUser } />
    );
  }
}

export default User;
