import React from 'react';

import Header from '../app/Header.tsx';
import Listing from '../app/Listing.tsx';
import T from 'i18n-react';

interface Props {
  response: ListResponse;
  onClientSelect: (id: number) => void;
  fetchClients: (query?: any) => void;
}

const Layout: React.StatelessComponent<Props> = props => {
  return (
    <div>
      <Header>
        <h2>{ T.translate('company.clients') }</h2>
      </Header>
      <div className='wrapper'>
        <div className='row'>
          <div className='col-xs-12'>
            <Listing
              onSelect={ props.onClientSelect }
              addNewRoute='/clients/new'
              fetchModels={ props.fetchClients }
              modelName='company'
              columns={ ['id', 'name', 'vatNumber'] }
              response={ props.response }
              authorizeAdd={ true }/>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Layout;
