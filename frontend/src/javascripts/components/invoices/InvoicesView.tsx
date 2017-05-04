import React from 'react';

import Header    from '../app/Header.tsx';
import Card      from '../app/Card.tsx';
import { th }    from '../../utils/utils.ts';
import InfoTable from '../tables/InfoTable.tsx';
import T from 'i18n-react';

interface Props {
  invoices : InvoiceData[];
  onInvoiceSelect : (id : number) => void;
}

const Overview : React.StatelessComponent<Props> = props => {
  const tableHead = [
    th('id',        'invoice.id'),
    th('startDate', 'invoice.startDate'),
    th('endDate',   'invoice.endDate'),
    th('paid',     'invoice.paid')
  ];

  return (
    <InfoTable head={ tableHead } data={ props.invoices } onClick={ props.onInvoiceSelect } />
  );
}

const MainCard : React.StatelessComponent<Props> = props => {
  return (
    <Card>
      <div className='card-content'>
        <Overview invoices={ props.invoices } onInvoiceSelect={ props.onInvoiceSelect } />
      </div>
    </Card>
  );
}

const InvoicesView : React.StatelessComponent<Props> = props => {
  return (
    <div>
      <Header>
        <h2>{ T.translate('invoice.invoices') }</h2>
      </Header>
      <div className='wrapper'>
        <div className='row'>
          <div className='col-xs-12'>
            <MainCard invoices={ props.invoices } onInvoiceSelect={ props.onInvoiceSelect } />
          </div>
        </div>
      </div>
    </div>
  );
}

export default InvoicesView;
