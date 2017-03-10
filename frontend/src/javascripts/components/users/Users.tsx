import React from 'react';
import { browserHistory } from'react-router';

import Card       from '../app/Card.tsx';
import Header     from '../app/Header.tsx';
import { InfoTable, th }  from '../tables/InfoTable.tsx';

import fetchUsers from '../../actions/fetch_users.ts';

interface OverviewProps {
  users: User[];
}

class Overview extends React.Component<OverviewProps, {}> {

  constructor() {
    super();
    this.handleClick = this.handleClick.bind(this);
  }

  public handleClick(id : number) {
    browserHistory.push('/users/' + id);
  }

  render() {
    const tableHead = [
      th('id',         'user.id'),
      th('first_name', 'user.first_name'),
      th('last_name',  'user.last_name')
    ];

    return (
      <InfoTable head={ tableHead } data={ this.props.users } onClick={ this.handleClick } />
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
