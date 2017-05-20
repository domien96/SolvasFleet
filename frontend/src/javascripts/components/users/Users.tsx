import React from 'react';
import Layout from './Layout.tsx';
import { fetchUsers } from '../../actions/user_actions.ts';
import { redirect_to } from '../../routes/router.tsx';

import { createQuery } from '../../utils/utils.ts';

interface State {
  response: ListResponse;
  filter: UserFilterData;
}

class Users extends React.Component<{}, State> {
  constructor(props: {}) {
    super(props);
    this.state = { response: {
      data: [],
      first: null,
      last: null,
      limit: 0,
      next: null,
      offset: 0,
      previous: null,
      total: 0,
    },
    filter: {
      firstName: '',
      lastName: '',
      email: '',
      archived: 'false'
    }};
    this.fetchUsers = this.fetchUsers.bind(this);
    this.handleFilter = this.handleFilter.bind(this);
  }

  componentDidMount() {
    this.fetchUsers(undefined, this.state.filter);
  }

  fetchUsers(query?: any, filter?: UserFilterData) {
    let newQuery = createQuery(query, filter);
    fetchUsers((data: any) => this.setState({ response: data }), undefined, newQuery);
  }

  handleClick(id: number) {
    redirect_to(`/users/${id}`);
  }

  handleFilter(newFilter: UserFilterData) {
    this.setState({ filter: newFilter });
    this.fetchUsers(undefined, newFilter);
  }

  render() {
    const children = React.Children.map(this.props.children, (child: any) => React.cloneElement(child, {
      fetchUsers: this.fetchUsers.bind(this),
    }));

    return (
      <Layout
        response={this.state.response}
        onUserSelect={ this.handleClick }
        fetchUsers={ this.fetchUsers }
        onFilter={ this.handleFilter } >
        { children }
      </Layout>
    );
  }
}

export default Users;
