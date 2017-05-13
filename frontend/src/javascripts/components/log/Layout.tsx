import React from 'react';
import T from 'i18n-react';
import Header from '../app/Header.tsx';
import LogListing from './LogListing.tsx';

interface Props {
  response: ListResponse;
  onLogSelect: (id: number) => void;
  fetchLog: (query?: any) => void;
}

const Layout: React.StatelessComponent<Props> = props => {
  return (
    <div>
      <Header>
        <h2>{ T.translate('log.log') }</h2>
      </Header>
      <div className='wrapper'>
        <div className='row'>
          <div className='col-xs-12 col-md-7'>
            <LogListing
              onSelect={ props.onLogSelect }
              fetchModels={ props.fetchLog }
              modelName='log'
              columns={ ['id', 'user', 'logDate'] }
              response={ props.response } />
          </div>
          <div className='col-xs-12 col-md-5'>
            { props.children }
          </div>
        </div>
      </div>
    </div>
  );
};

export default Layout;
