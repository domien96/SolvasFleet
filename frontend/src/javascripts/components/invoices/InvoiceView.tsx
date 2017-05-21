import React from 'react';

import Card from '../app/Card.tsx';
import DetailTable from '../tables/DetailTable.tsx';
import DownloadButton from '../buttons/DownloadButton.tsx';
import { th } from '../../utils/utils.ts';

interface Props {
  invoice: InvoiceData;
  onDownload: () => void;
  onTogglePaid: () => void;
}

const TogglePaid = ({ onToggle, paid }: { onToggle: () => void, paid: boolean }) => {

  let toggle = (
      <button onClick={ onToggle } className='btn btn-default form-control'>
        <span className='glyphicon glyphicon-ok' /> Set Invoice Paid
      </button>
    );
  if (paid) {
    toggle = (
     <button onClick={ onToggle } className='btn btn-default form-control'>
        <span className='glyphicon glyphicon-remove' /> Set Invoice Not Paid
      </button>
    );
  } 

  return toggle;
};

const InvoiceView: React.StatelessComponent<Props> = props => {
  const { id, fleet, paid, totalAmount, type, startDate, endDate } = props.invoice;

  const data = [
    th('invoice.id', id),
    th('invoice.fleet', fleet),
    th('invoice.paid', paid),
    th('invoice.totalAmount', totalAmount),
    th('invoice.type', type),
    th('invoice.startDate', startDate),
    th('invoice.endDate', endDate),
  ];

  return (
    <Card>
      <div className='card-content invoice'>
        <div className='row actions'>
          <div className='col-sm-6'>
            <DownloadButton onDownload={ props.onDownload } label='Download PDF'/>
          </div>
          <div className='col-sm-6'>
            <TogglePaid onToggle={ props.onTogglePaid } paid={ paid } />
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
