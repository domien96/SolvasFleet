import React from 'react';
import { Link } from'react-router';


import Card       from '../app/Card.tsx';
import WrappedCol from '../app/WrappedCol.tsx';
import InfoTable  from '../tables/InfoTable.tsx';

import fetchUsers from '../../actions/fetch_users.ts';

class Users extends React.Component<{}, UsersState> {

  constructor(props : {}) {
    super(props);
    this.state = { users: [] };
  }

  componentDidMount() {
    fetchUsers()
      .then((data : UsersData) => {
        this.setState({ users: data.users })
      });
  }

  render() {
    const tableHead = [
      { key: 'id', label: 'users.id' },
      { key: 'first_name', label: 'users.first_name' },
      { key: 'last_name', label: 'users.last_name' },
      { key: 'email', label: 'users.email' },
      { key: 'password', label: 'users.password' }
    ]

    return (
      <WrappedCol>
        <Card className='text-center' >
          <div className='card-title'>
            <h2>Users</h2>
            <Link to='users/new'>
              <span className='glyphicon glyphicon-plus' aria-hidden='true'></span>
            </Link>
          </div>
          <div className='card-content'>
            <InfoTable head={ tableHead } data={ this.state.users } />
          </div>
        </Card>
      </WrappedCol>
    );
  }
}

export default Users;
