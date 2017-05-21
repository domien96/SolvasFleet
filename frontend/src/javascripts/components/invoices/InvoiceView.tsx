import React from 'react';

import Card from '../app/Card.tsx';
import DetailTable from '../tables/DetailTable.tsx';
import DownloadButton from '../buttons/DownloadButton.tsx';
import { th } from '../../utils/utils.ts';

interface Props {
  invoice: InvoiceData;
  onDownload: () => void;
}

const InvoiceView: React.StatelessComponent<Props> = props => {
  const { id, fleet, paid, totalAmount, type, startDate, endDate } = props.invoice;

  let newStartDate = startDate
  let newEndDate = endDate
  if(startDate) {
    newStartDate = startDate.split('T')[0];
    newEndDate = endDate.split('T')[0];
  }
  const data = [
    th('invoice.id', id),
    th('invoice.fleet', fleet),
    th('invoice.paid', paid.toString()),
    th('invoice.totalAmount', totalAmount.toString()),
    th('invoice.type', type),
    th('invoice.startDate', newStartDate),
    th('invoice.endDate', newEndDate),
  ];

  return (
    <Card>
      <div className='card-content invoice'>
        <div className='row actions'>
          <div className='col-sm-6'>
            <DownloadButton onDownload={ props.onDownload } label='Download PDF'/>
          </div>
        </div>
      </div>
      <div className='card-content'>
        <DetailTable data={ data }/>
      </div>
    </Card>
  );
};

export default InvoiceView;
