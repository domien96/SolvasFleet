import React from 'react';
import Layout from './Layout.tsx';
import { fetchUsers } from '../../actions/user_actions.ts';
import { redirect_to } from'../../router.tsx';
interface State {
    response:ListResponse;
}

class Users extends React.Component<{}, State> {

  constructor(props : {}) {
    super(props);
    this.state = {response: {data:[],total:0,first:null,last:null,next:null,previous:null,limit:0,offset:0}}
    this.fetchUsers=this.fetchUsers.bind(this)
  }

  componentDidMount() {
    this.fetchUsers();
  }

  fetchUsers(query?:any) {
    fetchUsers((data : any) => {
      this.setState({ response:data
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
      <Layout response={this.state.response} onUserSelect={ this.handleClick } fetchUsers={this.fetchUsers} >
        { children }
      </Layout>
    );
  }
}

export default Users;
