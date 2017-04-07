import React from 'react';

import Overview, { Props } from './Overview.tsx';
import Header    from '../app/Header.tsx';
import Card      from '../app/Card.tsx';
import Pagination from '../pagination/Pagination.tsx';
import { Link } from 'react-router';

const MainCard : React.StatelessComponent<Props> = props => {
  return (
    <Card>
      <div className='card-content'>
        <Link to='/clients/new' className='btn btn-default pull-right'>
          <span className='glyphicon glyphicon-plus' aria-hidden='true'></span> Add new client
        </Link>
        <Overview clients={ props.clients } onClientSelect={ props.onClientSelect } fetchClients={props.fetchClients}/>
        <Pagination onClick={props.fetchClients}/>
      </div>
    </Card>
  );
}

const Layout : React.StatelessComponent<Props> = props => {
  return (
    <div>
      <Header>
        <h2>clients</h2>
      </Header>
      <div className='wrapper'>
        <div className='row'>
          <div className='col-xs-12'>
            <MainCard clients={ props.clients } onClientSelect={ props.onClientSelect } fetchClients={props.fetchClients}/>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Layout;
