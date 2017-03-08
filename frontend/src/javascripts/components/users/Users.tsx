import React from 'react';
import { Link } from'react-router';

import fetchUsers from '../../actions/fetch_users.ts';

import Card       from '../app/Card.tsx';
import WrappedCol from '../app/WrappedCol.tsx';

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
    const users = this.state.users.map((u, i) => {
      return (<div key={ i }>{ u.first_name }</div>);
    });

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
            { users }
          </div>
        </Card>
      </WrappedCol>
    );
  }
}

export default Users;
