import React from 'react';

import Header from '../app/Header.tsx';
import T from 'i18n-react';
import InvoicesListing from './InvoicesListing.tsx';

interface Props {
  response: ListResponse;
  tableData: any;
  onInvoiceSelect: (id: number) => void;
  handleFetchInvoices: (query?: any) => void;
}

const InvoicesView: React.StatelessComponent<Props> = props => {
  return (
    <div>
      <Header>
        <h2>{ T.translate('invoice.invoices') }</h2>
      </Header>
      <div className='wrapper'>
        <div className='row'>
          <div className='col-xs-12'>
            <InvoicesListing
              onSelect={ props.onInvoiceSelect }
              fetchModels={ props.handleFetchInvoices }
              modelName='invoice'
              columns={ ['id', 'startDate', 'endDate', 'paid'] }
              response={ props.response } 
              tableData={ props.tableData } />
          </div>
        </div>
      </div>
    </div>
  );
};

export default InvoicesView;
