import React from 'react';

import Card from '../app/Card.tsx';
import DetailTable from '../tables/DetailTable.tsx';

import { th } from '../../utils/utils.ts';

interface DownloadButtonProps {
  onDownload : () => void;
}

const DownloadButton : React.StatelessComponent<DownloadButtonProps> = props => {
  return (
    <div className='col-sm-6'>
      <button onClick={ props.onDownload } className='btn btn-default form-control'>
        <span className='glyphicon glyphicon glyphicon-download-alt' /> Download PDF
      </button>
    </div>
  );
}

interface Props {
  invoice : InvoiceData;
  onDownload : () => void;
}

const InvoiceView : React.StatelessComponent<Props> = props => {
  var { id, fleet, paid, totalAmount, type, startDate, endDate } = props.invoice;

  const data = [
    th('invoice.id', id),
    th('invoice.fleet', fleet),
    th('invoice.paid', paid),
    th('invoice.totalAmount', totalAmount),
    th('invoice.type', type),
    th('invoice.startDate', startDate),
    th('invoice.endDate', endDate)
  ];

  return (
    <Card>
      <div className='card-content invoice'>
        <div className='row actions'>
          <DownloadButton onDownload={ props.onDownload } />
        </div>
      </div>
      <div className='card-content'>
        <DetailTable data={ data }/>
      </div>
    </Card>
  );
}

export default InvoiceView;
