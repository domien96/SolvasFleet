import React from 'react';
import Layout from './Layout.tsx';
import { fetchUsers } from '../../actions/user_actions.ts';
import { redirect_to } from'../../router.tsx';
interface State {
    users : UserData[];
    pagresponse : PaginationResponse;
}

class Users extends React.Component<{}, State> {

  constructor(props : {}) {
    super(props);
    this.state = {
      users: [],
      pagresponse:{total:0,first : 0, last : 0, limit : 0, offset : 0, previous : 0, next : 0}
    }
    this.fetchUsers=this.fetchUsers.bind(this)
  }

  componentDidMount() {
    this.fetchUsers();
  }

  fetchUsers(query?:any) {
    fetchUsers((data : any) => {
      this.setState({ users: data.data,
        pagresponse : data
      })
    },undefined,query);
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
      <Layout users={ this.state.users } onUserSelect={ this.handleClick } fetchUsers={this.fetchUsers} >
        { children }
      </Layout>
    );
  }
}

export default Users;
