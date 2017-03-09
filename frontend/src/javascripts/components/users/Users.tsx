import React from 'react';
import { browserHistory } from'react-router';

import Card       from '../app/Card.tsx';
import Header     from '../app/Header.tsx';
import InfoTable  from '../tables/InfoTable.tsx';

import fetchUsers from '../../actions/fetch_users.ts';

interface OverviewProps {
  users: User[];
}

class Overview extends React.Component<OverviewProps, {}> {

  constructor(){
    super();
    this.handleClick = this.handleClick.bind(this);
  }

  public handleChange(u : User) {
    browserHistory.push('/users/' + u.id);
  }

  handleClick(e : any){
    const id = e.item['id'];
    console.log("handling click id = "+ e.item['id']+ "")
    browserHistory.push('/users/' + id);
    console.log("click got handled")
  }

  render() {

    const tableHead = [
      { key: 'id', label: 'user.id' },   
      { key: 'first_name', label: 'user.first_name' },   
      { key: 'last_name', label: 'user.last_name' }     
    ]

    return (
      <InfoTable head={ tableHead } data={ this.props.users } onClick={(e : any) => this.handleClick(e)} />
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
