import React from 'react';
import { browserHistory } from'react-router';

import Card       from '../app/Card.tsx';
import Header     from '../app/Header.tsx';

import fetchUsers from '../../actions/fetch_users.ts';

interface OverviewProps {
  users: User[];
}

class Overview extends React.Component<OverviewProps, {}> {
  public handleChange(u : User) {
    browserHistory.push('/users/' + u.id);
  }

  render() {
    const rows = this.props.users.map((u, i) => {
      return (
        <tr key={ i } onClick={ this.handleChange.bind(this, u) } >
          <td>{ u.first_name || ''} { u.last_name || '' }</td>
          <td>{ u.email || ''}</td>
        </tr>
      );
    });

    return (
      <table className='table table-striped'>
        <tbody>
          { rows }
        </tbody>
      </table>
    );
  }
}

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
    return (
      <div>
        <Header>
          <h2>Users</h2>
        </Header>
        <div className='wrapper'>
          <div className='row'>
            <div className='col-xs-12 col-md-7'>
              <Card>
                <div className='card-content'>
                  <Overview users={ this.state.users } />
                </div>
              </Card>
            </div>
            <div className='col-xs-12 col-md-5'>
              <Card>
                { this.props.children }
              </Card>
            </div>
          </div>
        </div>
      </div>
    );
  }
}

export default Users;
