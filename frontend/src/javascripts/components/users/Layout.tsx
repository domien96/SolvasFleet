import React from 'react';

import Header    from '../app/Header.tsx';
import Listing from '../app/Listing.tsx';

interface Props {
  users : UserData[];
  onUserSelect : (id : number) => void;
  fetchUsers : (query?:any)=>void;
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
            <Listing onSelect={props.onUserSelect} addNewRoute='/users/new' fetchModels={props.fetchUsers} modelName='user' columns={['id','firstName','lastName']} models={props.users}/>
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
