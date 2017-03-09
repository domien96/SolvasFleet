import React from 'react';

import Card       from '../app/Card.tsx';
import WrappedCol from '../app/WrappedCol.tsx';

import fetchUser from '../../actions/fetch_user.ts';

class User extends React.Component<{}, UserData> {

  componentDidMount() {
    const id = this.props.params.id;
    fetchUser( id )
      .then((data : UserData) => {
        this.setState({ user: data.user })
      });
  }

  render() {

    const u = this.state.user;

    return (
      <WrappedCol>
        <Card className='text-center' >
          <div className='card-content'>
            <h2> User </h2>
            <button>Edit User</button>
            <ul>
              <li key = 'name'>{u.first_name} {u.last_name}</li>
              <li key = 'email'>{u.email}</li>
              <li key = 'password'>{u.password}</li>
            </ul>
          </div>
        </Card>
      </WrappedCol>
    );
  }
}

export default User;

