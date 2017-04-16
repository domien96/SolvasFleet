import React from 'react';

import Header    from '../app/Header.tsx';
import Listing from '../app/Listing.tsx';

interface Props {
  clients : Company[];
  onClientSelect : (id : number) => void;
  fetchClients : (query?:any)=>void;
}

const Layout : React.StatelessComponent<Props> = props => {
  return (
    <div>
      <Header>
        <h2>Clients</h2>
      </Header>
      <div className='wrapper'>
        <div className='row'>
          <div className='col-xs-12'>
            <Listing onSelect={props.onClientSelect} addNewRoute='/users/new' fetchModels={props.fetchClients} modelName='company'
              columns={['id','name','vatNumber']} models={props.clients}/>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Layout;
