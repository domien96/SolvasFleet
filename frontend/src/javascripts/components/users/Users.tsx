import React              from 'react';
import { browserHistory } from'react-router';

import Layout from './Layout.tsx';

import fetchUsers from '../../actions/fetch_users.ts';

class Users extends React.Component<{}, Users.State> {

  constructor(props : {}) {
    super(props);
    this.state = { users: [] };
  }

  componentDidMount() {
    this.fetchUsers();
  }

  fetchUsers() {
    fetchUsers()
      .then((data : Users.Data) => {
        this.setState({ users: data.data })
      });
  }

  handleClick(id : number) {
    browserHistory.push('/users/' + id);
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
