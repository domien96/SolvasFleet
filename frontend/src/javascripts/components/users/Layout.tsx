import React from 'react';

import Overview, { Props } from './Overview.tsx';
import Header    from '../app/Header.tsx';
import Card      from '../app/Card.tsx';
import { Link } from 'react-router';
import Pagination from '../pagination/Pagination.tsx';

const MainCard : React.StatelessComponent<Props> = props => {
  return (
    <Card>
      <div className='card-content'>
        <Link to='/users/new' className='btn btn-default pull-right'>
          <span className='glyphicon glyphicon-plus' aria-hidden='true'></span> Add new user
        </Link>
        <Overview users={ props.users } onUserSelect={ props.onUserSelect } fetchUsers={props.fetchUsers} />
        <Pagination onClick={props.fetchUsers}/>
      </div>
    </Card>
  );
}

const Layout : React.StatelessComponent<Props> = props => {
  return (
    <div>
      <Header>
        <h2>Users</h2>
      </Header>
      <div className='wrapper'>
        <div className='row'>
          <div className='col-xs-12 col-md-7'>
            <MainCard users={ props.users } onUserSelect={ props.onUserSelect } fetchUsers={props.fetchUsers} />
          </div>
          <div className='col-xs-12 col-md-5'>
            { props.children }
          </div>
        </div>
      </div>
    </div>
  );
}

export default Layout;
