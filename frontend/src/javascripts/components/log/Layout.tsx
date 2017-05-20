import React from 'react';
import T from 'i18n-react';
import Header from '../app/Header.tsx';
import LogListing from './LogListing.tsx';
import LogFilter from './filters/LogFilter.tsx'

interface Props {
  response: ListResponse;
  onLogSelect: (id: number) => void;
  fetchLog: (query: any) => void;
  tableData: any;
  onFilter: (filter: LogFilterData) => void;
  getUser: (users: UserData[], id: number, init?: boolean) => string;
}

const Layout: React.StatelessComponent<Props> = props => {
  return (
    <div>
      <Header>
        <h2>{ T.translate('log.log') }</h2>
      </Header>
      <div className='wrapper'>
        <div className='row'>
          <div className='col-xs-12'>
            <LogFilter 
              logs={ props.response.data } 
              onFilter={ props.onFilter } 
              getUser={ props.getUser } />
            <LogListing
              onSelect={ props.onLogSelect }
              fetchModels={ props.fetchLog }
              modelName='log'
              columns={ ['logDate', 'method', 'entityType', 'user'] }
              response={ props.response } 
              tableData={ props.tableData } />
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
