import React              from 'react';

import Layout from './Layout.tsx';

import { fetchUsers } from '../../actions/user_actions.ts';
import { redirect_to } from'../../routes/router.tsx';

interface State {
    users : UserData[];
}

class Users extends React.Component<{}, State> {

  constructor(props : {}) {
    super(props);
    this.state = { users: [] };
  }

  componentDidMount() {
    this.fetchUsers();
  }

  fetchUsers() {
    fetchUsers((data : Users.Data) => {
      this.setState({ users: data.data })
    });
  }

  handleClick(id : number) {
    redirect_to(`/users/${id}`);
  }

  render() {
    const children = React.Children.map(this.props.children,
      (child : any) => React.cloneElement(child, {
        fetchUsers: this.fetchUsers.bind(this)
      })
    );

    return (
      <Layout users={ this.state.users } onUserSelect={ this.handleClick } >
        { children }
      </Layout>
    );
  }
}

export default Users;
